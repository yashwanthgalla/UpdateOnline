package com.banking.app.controller;

import com.banking.app.dto.ApiResponse;
import com.banking.app.entity.Account;
import com.banking.app.service.AccountService;
import com.banking.app.service.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AccountController {
    
    @Autowired
    private AccountService accountService;
    
    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@AuthenticationPrincipal UserPrincipal userPrincipal,
                                         @RequestParam Account.AccountType accountType) {
        try {
            Account account = accountService.createAccount(userPrincipal.getId(), accountType);
            return ResponseEntity.ok(new ApiResponse(true, "Account created successfully!", account));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/my-accounts")
    public ResponseEntity<?> getUserAccounts(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        try {
            List<Account> accounts = accountService.getUserAccounts(userPrincipal.getId());
            return ResponseEntity.ok(new ApiResponse(true, "Accounts retrieved successfully!", accounts));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @GetMapping("/{accountNumber}")
    public ResponseEntity<?> getAccountByNumber(@PathVariable String accountNumber) {
        try {
            Account account = accountService.getAccountByNumber(accountNumber)
                    .orElseThrow(() -> new RuntimeException("Account not found"));
            return ResponseEntity.ok(new ApiResponse(true, "Account found!", account));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
    
    @PutMapping("/{accountNumber}/deactivate")
    public ResponseEntity<?> deactivateAccount(@PathVariable String accountNumber) {
        try {
            Account account = accountService.deactivateAccount(accountNumber);
            return ResponseEntity.ok(new ApiResponse(true, "Account deactivated successfully!", account));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse(false, "Error: " + e.getMessage()));
        }
    }
}