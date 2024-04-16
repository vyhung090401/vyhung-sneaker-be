package com.demo.angular.serviceImp;

import com.demo.angular.payload.request.ChangePasswordReq;
import com.demo.angular.model.Account;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public boolean validateCurrentPassword(ChangePasswordReq changePasswordReq) {
        try {
        // Lấy user hiện tại
        UserDetails userDetails =
                (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Account account = accountRepository.findByUsername(userDetails.getUsername());
//        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
//                account.getUsername(),
//                currentPassword
//        );
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(account.getUsername(), changePasswordReq.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication.isAuthenticated();

        } catch (BadCredentialsException badCredentialsException) {
            return false;
        }

    }

    @Override
    public void changePassword(ChangePasswordReq changePasswordReq){
        Account account = accountRepository.findByUsername(changePasswordReq.getUsername());
        account.setPassword(passwordEncoder.encode(changePasswordReq.getPassword()));
        accountRepository.save(account);
    }
}
