package com.banking.app.controller;

import com.banking.app.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class HomeController {
    
    @GetMapping("/")
    public ResponseEntity<?> home() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Welcome to Banking Application API");
        response.put("version", "1.0.0");
        response.put("timestamp", LocalDateTime.now());
        response.put("endpoints", Map.of(
                "auth", "/auth (POST /signin, POST /signup)",
                "accounts", "/accounts (GET /my-accounts, POST /create)",
                "transactions", "/transactions (POST /transfer, POST /deposit, POST /withdraw)",
                "user", "/user (GET /profile, PUT /profile)"
        ));
        
        return ResponseEntity.ok(new ApiResponse(true, "Banking API is running!", response));
    }
    
    @GetMapping("/health")
    public ResponseEntity<?> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now());
        health.put("service", "Banking Application");
        
        return ResponseEntity.ok(health);
    }
}