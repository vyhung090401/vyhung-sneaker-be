package com.demo.angular.serviceImp;

import com.demo.angular.dto.AccountDTO;
import com.demo.angular.dto.CustomerDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Product;
import com.demo.angular.repository.AccountRepository;
import com.demo.angular.repository.CustomerRepository;
import com.demo.angular.service.AccountService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImp implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Page<AccountDTO> getAccounts(int page, int size) {
        Page<Account> accountPage = accountRepository.findAll(PageRequest.of(page, size));
        List<Account> accounts = accountPage.getContent();
        List<AccountDTO> accountDTOList = new ArrayList<>();
        for(int i=0; i < accounts.size(); i++){
            AccountDTO accountDTO = new AccountDTO(accounts.get(i));
            accountDTOList.add(accountDTO);
        }
        Pageable pageable = PageRequest.of(page,size);
        Page<AccountDTO> accountDTOPage = new PageImpl<>(accountDTOList, pageable, accountDTOList.size());
        return accountDTOPage;
    }

    @Override
    public Account updateAccount(Account account) {
        return accountRepository.save(account);
    }

    @Override
    public AccountDTO banAccountById(Long id){
        Account account = accountRepository.findById(id).get();
        account.setNotLocked(false);
        accountRepository.save(account);
        AccountDTO accountDTO = new AccountDTO(account);
        return accountDTO;
    }

    @Override
    public AccountDTO unbanAccountById(Long id){
        Account account = accountRepository.findById(id).get();
        account.setNotLocked(true);
        accountRepository.save(account);
        AccountDTO accountDTO = new AccountDTO(account);
        return accountDTO;
    }

    @Override
    public AccountDTO getAccountByPhone(String phoneNumber){
        Account account = accountRepository.findByCustomerPhone(phoneNumber);
        if(account == null) {
            throw new RuntimeException("Phone number:  " + phoneNumber + " is not exist");
        }
        AccountDTO accountDTO = new AccountDTO(account);
        accountDTO.setPhone(customerRepository.findById(account.getId()).get().getPhone());
        return accountDTO;
    }

    @Override
    public void increaseFailedAttempts(Account account) {
        if(account.getFailedAttempt() == null || account.getFailedAttempt() == 0){
            account.setFailedAttempt(1);
        }else{
            account.setFailedAttempt(account.getFailedAttempt() + 1);
        }
        accountRepository.save(account);
    }

    @Override
    public void resetFailedAttempts(Long id) {
        Account account = accountRepository.findById(id).get();
        if(account.getFailedAttempt() != null && account.getFailedAttempt() > 0){
            account.setFailedAttempt(0);
            accountRepository.save(account);
        }
    }

    @Override
    public void lock(Account account) {
        account.setNotLocked(false);
        account.setLockTime(new Date());

        accountRepository.save(account);
    }

    @Override
    public boolean unlockWhenTimeExpired(Account account) {
        long lockTimeInMillis = account.getLockTime().getTime();
        long currentTimeInMillis = System.currentTimeMillis();

        if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
            account.setNotLocked(true);
            account.setLockTime(null);
            account.setFailedAttempt(0);

            accountRepository.save(account);

            return true;
        }

        return false;
    }

}
