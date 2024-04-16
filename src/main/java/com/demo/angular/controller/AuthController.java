package com.demo.angular.controller;

import com.demo.angular.config.jwt.JwtUtils;
import com.demo.angular.config.service.UserDetailsImp;
import com.demo.angular.payload.request.ChangePasswordReq;
import com.demo.angular.exception.NotAcceptableException;
import com.demo.angular.model.Account;
import com.demo.angular.model.Customer;
import com.demo.angular.model.ERole;
import com.demo.angular.model.Role;
import com.demo.angular.payload.response.MessageResponse;
import com.demo.angular.payload.response.ObjectResponse;
import com.demo.angular.payload.response.UserInfoResponse;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.repository.CustomerRepository;
import com.demo.angular.repository.RoleRepository;
import com.demo.angular.payload.request.LoginRequest;
import com.demo.angular.payload.request.SignupRequest;
import com.demo.angular.service.AccountService;
import com.demo.angular.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    AuthService authService;

    @Autowired
    AccountService accountService;

    @PostMapping("/signin")
    public ResponseEntity<ObjectResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateTokenFromUsername(authentication);

//        ResponseCookie jwtCookie = jwtUtils.generateJwtCookie(userDetails);

            UserDetailsImp userDetails = (UserDetailsImp) authentication.getPrincipal();

            accountService.resetFailedAttempts(userDetails.getId());

            if(!userDetails.isAccountNonLocked()) {
                throw new NotAcceptableException("Account is locked!");
            }

            List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());
            Account account = accountRepository.findById(userDetails.getId()).get();
            account.setActive(true);
            accountRepository.save(account);
            System.out.println(userDetails.getBirthday());
            return ResponseEntity.ok()
                .body(new ObjectResponse("ok","Login successfull!",new UserInfoResponse(jwt,userDetails.getId(),
                    userDetails.getUsername(),
                    userDetails.getPhone(),
                    roles,
                    userDetails.getBirthday(),
                    userDetails.getAddress(),
                    userDetails.getGender()
                )
                ));
        } catch (BadCredentialsException e) {
            Account account = accountRepository.findByUsername(loginRequest.getUsername());
            if (account != null) {
                if (account.isNotLocked()) {
                    if (account.getFailedAttempt() == null ||
                        account.getFailedAttempt() < AccountService.MAX_FAILED_ATTEMPTS - 1) {
                        accountService.increaseFailedAttempts(account);
                    } else {
                        accountService.lock(account);
                        return ResponseEntity.badRequest().body(new ObjectResponse("failed", "Your account has been locked due to 5 failed attempts."
                            + " It will be unlocked after 15 minutes.", ""));
                    }
                }
            }
            return ResponseEntity.badRequest().body(new ObjectResponse("failed", "Username or password invalid!", ""));
        } catch (LockedException e) {
            return ResponseEntity.badRequest().body(new ObjectResponse("failed", e.getMessage()+"1", ""));
        } catch (DisabledException e) {
            return ResponseEntity.badRequest().body(new ObjectResponse("failed",e.getMessage()+"2", ""));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (accountRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
        }

        if (customerRepository.existsByPhone(signUpRequest.getPhone())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Phone is already in use!"));
        }

        // Create new user's account
        Account account = new Account(signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));
        // Create new user's account
        Customer customer = new Customer(signUpRequest.getAddress(),
                signUpRequest.getPhone(),
                signUpRequest.getGender(),
                signUpRequest.getBirthday());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        account.setNotLocked(true);
        account.setRoles(roles);
        accountRepository.save(account);
        customer.setAccount(account);
        customerRepository.save(customer);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        UserDetails userDetails =
            (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername());
        account.setActive(false);
        accountRepository.save(account);
        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
        return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(new MessageResponse("You've been signed out!"));
    }

    @PostMapping("/validatePassword")
    public boolean validatePassword(@RequestBody ChangePasswordReq currentPassword) {
        return authService.validateCurrentPassword(currentPassword);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordReq currentPassword) {
        try {
            authService.changePassword(currentPassword);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
}
