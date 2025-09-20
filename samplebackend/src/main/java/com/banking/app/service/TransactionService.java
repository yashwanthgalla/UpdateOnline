package com.banking.app.service;

import com.banking.app.entity.Account;
import com.banking.app.entity.Transaction;
import com.banking.app.repository.AccountRepository;
import com.banking.app.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class TransactionService {
    
    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public Transaction transferMoney(String fromAccountNumber, String toAccountNumber, 
                                   BigDecimal amount, String description) {
        Account fromAccount = accountRepository.findByAccountNumber(fromAccountNumber)
                .orElseThrow(() -> new RuntimeException("Source account not found"));
        
        Account toAccount = accountRepository.findByAccountNumber(toAccountNumber)
                .orElseThrow(() -> new RuntimeException("Destination account not found"));
        
        // Check if source account has sufficient balance
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Debit from source account
        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        accountRepository.save(fromAccount);
        
        // Credit to destination account
        toAccount.setBalance(toAccount.getBalance().add(amount));
        accountRepository.save(toAccount);
        
        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionType(Transaction.TransactionType.TRANSFER);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setFromAccount(fromAccount);
        transaction.setToAccount(toAccount);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction deposit(String accountNumber, BigDecimal amount, String description) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        // Credit to account
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
        
        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionType(Transaction.TransactionType.DEPOSIT);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setToAccount(account);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        
        return transactionRepository.save(transaction);
    }
    
    public Transaction withdraw(String accountNumber, BigDecimal amount, String description) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        // Check if account has sufficient balance
        if (account.getBalance().compareTo(amount) < 0) {
            throw new RuntimeException("Insufficient balance");
        }
        
        // Debit from account
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
        
        // Create transaction record
        Transaction transaction = new Transaction();
        transaction.setTransactionId(generateTransactionId());
        transaction.setTransactionType(Transaction.TransactionType.WITHDRAWAL);
        transaction.setAmount(amount);
        transaction.setDescription(description);
        transaction.setFromAccount(account);
        transaction.setStatus(Transaction.TransactionStatus.COMPLETED);
        transaction.setReferenceNumber(UUID.randomUUID().toString());
        
        return transactionRepository.save(transaction);
    }
    
    public List<Transaction> getAccountTransactions(String accountNumber) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return transactionRepository.findByFromAccountOrToAccountOrderByCreatedAtDesc(account, account);
    }
    
    public Page<Transaction> getAccountTransactionsPaginated(String accountNumber, Pageable pageable) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return transactionRepository.findByFromAccountOrToAccountOrderByCreatedAtDesc(
                account, account, pageable);
    }
    
    public List<Transaction> getAccountTransactionsBetweenDates(String accountNumber, 
                                                               LocalDateTime startDate, 
                                                               LocalDateTime endDate) {
        Account account = accountRepository.findByAccountNumber(accountNumber)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        
        return transactionRepository.findAccountTransactionsBetweenDates(account, startDate, endDate);
    }
    
    private String generateTransactionId() {
        return "TXN" + System.currentTimeMillis();
    }
}