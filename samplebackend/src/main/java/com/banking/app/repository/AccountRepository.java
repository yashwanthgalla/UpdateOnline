package com.banking.app.repository;

import com.banking.app.entity.Account;
import com.banking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    
    Optional<Account> findByAccountNumber(String accountNumber);
    
    List<Account> findByUser(User user);
    
    List<Account> findByUserAndIsActive(User user, Boolean isActive);
    
    boolean existsByAccountNumber(String accountNumber);
    
    List<Account> findByAccountType(Account.AccountType accountType);
}