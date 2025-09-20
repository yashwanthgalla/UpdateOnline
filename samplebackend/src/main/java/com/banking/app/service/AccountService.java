package com.banking.app.service;

import com.banking.app.entity.Account;
import com.banking.app.entity.User;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class AccountService {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    public Account createAccount(Long userId, Account.AccountType accountType) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Account account = new Account();
        account.setAccountNumber(generateAccountNumber());
        account.setAccountType(accountType);
        account.setBalance(BigDecimal.ZERO);
        account.setIsActive(true);
        account.setUser(user);
        
        // Set default values based on account type
        setAccountDefaults(account, accountType);
        
        return accountRepository.save(account);
    }
    
    private void setAccountDefaults(Account account, Account.AccountType accountType) {
        switch (accountType) {
            case SAVINGS:
                account.setInterestRate(3.5);
                account.setMinimumBalance(new BigDecimal("1000"));
                break;
            case CURRENT:
                account.setInterestRate(0.0);
                account.setMinimumBalance(new BigDecimal("5000"));
                account.setOverdraftLimit(new BigDecimal("50000"));
                break;
            case SALARY:
                account.setInterestRate(3.0);
                account.setMinimumBalance(BigDecimal.ZERO);
                break;
            case FIXED_DEPOSIT:
                account.setInterestRate(6.5);
                account.setMinimumBalance(new BigDecimal("10000"));
                break;
        }
    }
    
    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = "ACC" + System.currentTimeMillis() + 
                           String.format("%04d", new Random().nextInt(10000));
        } while (accountRepository.existsByAccountNumber(accountNumber));
        
        return accountNumber;
    }
    
    public List<Account> getUserAccounts(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return accountRepository.findByUser(user);
    }
    
    public Optional<Account> getAccountByNumber(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }
    
    public Account updateBalance(String accountNumber, BigDecimal amount) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setBalance(account.getBalance().add(amount));
        return accountRepository.save(account);
    }
    
    public Account deactivateAccount(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        account.setIsActive(false);
        return accountRepository.save(account);
    }
}