package com.demo.angular.controller;


import com.demo.angular.dto.AccountDTO;
import com.demo.angular.exception.ResourceNotFoundException;
import com.demo.angular.model.Account;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.service.AccountService;
import com.demo.angular.serviceImp.AccountServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/")

public class AccountController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountServiceImp accountServiceImp;



    //get all products
    @GetMapping("/accounts")
    public ResponseEntity<?> getAccountsList(@RequestParam(name = "page", defaultValue = "0") int page,
                                             @RequestParam(name = "size", defaultValue = "5") int size) {
        try{
             Page<AccountDTO> accounts = accountServiceImp.getAccounts(page, size);
            return ResponseEntity.ok(accounts);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        return null;
    }

    //create product
    @PostMapping("/accounts")
    public Account CreateAccount(@RequestBody Account account){
        return accountRepository.save(account);
    }

    //get product by ID
    @GetMapping("/accounts/{id}")
    public ResponseEntity<?> getAccountById(@PathVariable Long id){
        Account account= accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not exist with id: "+id));
        AccountDTO accountDTO = new AccountDTO(account);
        return ResponseEntity.ok(accountDTO);

    }

    //update product by ID
    @PutMapping("/accounts/{id}")
    public ResponseEntity<Account> updateAccountById(@PathVariable Long id,@RequestBody Account accountDetails ) {
        accountDetails.setId(id);
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not exist with id: " + id));

        Account accountUpdate = account;
        accountUpdate = accountServiceImp.updateAccount(accountDetails);
        return ResponseEntity.ok(accountUpdate);
    }

    @PutMapping("/accounts/lock/{id}")
    public ResponseEntity<Account> lockAccountById(@PathVariable Long id){
        Account account= accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product is not exist with id: "+id));
        account.setNotLocked(false);
        accountRepository.save(account);
        return ResponseEntity.ok(account);

    }

    @PutMapping("/accounts/ban/{id}")
    public ResponseEntity<AccountDTO> banAccountById(@PathVariable Long id) {
        AccountDTO accountDTO = accountService.banAccountById(id);
        return ResponseEntity.ok(accountDTO);
    }

    @PutMapping("/accounts/unban/{id}")
    public ResponseEntity<AccountDTO> unbanAccountById(@PathVariable Long id) {
        AccountDTO accountDTO = accountService.unbanAccountById(id);
        return ResponseEntity.ok(accountDTO);
    }
    @GetMapping("/accounts/findByPhone")
    public ResponseEntity<?> getAccountByPhone(@RequestParam String phoneNumber){
        try {
            AccountDTO accountDTO = accountService.getAccountByPhone(phoneNumber);
            return ResponseEntity.ok(accountDTO);
        } catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
