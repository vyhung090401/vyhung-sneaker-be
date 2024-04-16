package com.demo.angular.service;

import com.demo.angular.dto.AccountDTO;
import com.demo.angular.model.Account;
import com.demo.angular.model.Product;
import org.springframework.data.domain.Page;

import java.util.Date;


public interface AccountService {


    int MAX_FAILED_ATTEMPTS = 5;

    long LOCK_TIME_DURATION = 15 * 60 * 1000; // 15 minutes

    // Định nghĩa các phương thức tương ứng với chức năng quản lý tài khoản
    Page<AccountDTO> getAccounts(int page, int size);
    Account updateAccount(Account account);

    AccountDTO banAccountById(Long id);

    AccountDTO unbanAccountById(Long id);

    AccountDTO getAccountByPhone(String phoneNumber);

    void increaseFailedAttempts(Account account);

    void resetFailedAttempts(Long id);

    void lock(Account account);

    boolean unlockWhenTimeExpired(Account account);

}