package com.banking.app.controller;

import com.banking.app.dto.ApiResponse;
import com.banking.app.entity.Transaction;
import com.banking.app.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/transactions")
@CrossOrigin(origins = "*", maxAge = 3600)
public class TransactionController {
    
    @Autowired
    private TransactionService transactionService;
    
    @PostMapping("/transfer")
    public ResponseEntity<?> transferMoney(@RequestParam String fromAccountNumber,
                                         @RequestParam String toAccountNumber,
                                         @RequestParam BigDecimal amount,
                                         @RequestParam(required = false) String description) {
        try {
            Transaction transaction = transactionService.transferMoney(
                    fromAccountNumber, toAccountNumber, amount, description);
            return ResponseEntity.ok(new ApiResponse(true, "Money transferred successfully!", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/deposit")
    public ResponseEntity<?> deposit(@RequestParam String accountNumber,
                                   @RequestParam BigDecimal amount,
                                   @RequestParam(required = false) String description) {
        try {
            Transaction transaction = transactionService.deposit(accountNumber, amount, description);
            return ResponseEntity.ok(new ApiResponse(true, "Amount deposited successfully!", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @PostMapping("/withdraw")
    public ResponseEntity<?> withdraw(@RequestParam String accountNumber,
                                    @RequestParam BigDecimal amount,
                                    @RequestParam(required = false) String description) {
        try {
            Transaction transaction = transactionService.withdraw(accountNumber, amount, description);
            return ResponseEntity.ok(new ApiResponse(true, "Amount withdrawn successfully!", transaction));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<?> getAccountTransactions(@PathVariable String accountNumber,
                                                   @RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        try {
            if (page >= 0 && size > 0) {
                Pageable pageable = PageRequest.of(page, size);
                Page<Transaction> transactions = transactionService.getAccountTransactionsPaginated(
                        accountNumber, pageable);
                return ResponseEntity.ok(new ApiResponse(true, "Transactions retrieved successfully!", transactions));
            } else {
                List<Transaction> transactions = transactionService.getAccountTransactions(accountNumber);
                return ResponseEntity.ok(new ApiResponse(true, "Transactions retrieved successfully!", transactions));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/account/{accountNumber}/between-dates")
    public ResponseEntity<?> getAccountTransactionsBetweenDates(@PathVariable String accountNumber,
                                                               @RequestParam String startDate,
                                                               @RequestParam String endDate) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime start = LocalDateTime.parse(startDate, formatter);
            LocalDateTime end = LocalDateTime.parse(endDate, formatter);
            
            List<Transaction> transactions = transactionService.getAccountTransactionsBetweenDates(
                    accountNumber, start, end);
            return ResponseEntity.ok(new ApiResponse(true, "Transactions retrieved successfully!", transactions));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
}