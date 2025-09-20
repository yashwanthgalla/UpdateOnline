package com.banking.app.service;

import com.banking.app.dto.AuthResponse;
import com.banking.app.dto.LoginRequest;
import com.banking.app.dto.SignupRequest;
import com.banking.app.entity.User;
import com.banking.app.repository.UserRepository;
import com.banking.app.config.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    
    @Autowired
    AuthenticationManager authenticationManager;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    PasswordEncoder encoder;
    
    @Autowired
    JwtUtils jwtUtils;
    
    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
        
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateToken((UserPrincipal) authentication.getPrincipal());
        
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        
        return new AuthResponse(jwt,
                userPrincipal.getId(),
                userPrincipal.getEmail(),
                userPrincipal.getFirstName(),
                userPrincipal.getLastName(),
                userPrincipal.getAuthorities().iterator().next().getAuthority());
    }
    
    public User registerUser(SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new RuntimeException("Error: Email is already taken!");
        }
        
        if (signUpRequest.getPanNumber() != null && 
            userRepository.existsByPanNumber(signUpRequest.getPanNumber())) {
            throw new RuntimeException("Error: PAN number is already registered!");
        }
        
        if (signUpRequest.getAadharNumber() != null && 
            userRepository.existsByAadharNumber(signUpRequest.getAadharNumber())) {
            throw new RuntimeException("Error: Aadhar number is already registered!");
        }
        
        // Create new user's account
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(encoder.encode(signUpRequest.getPassword()));
        user.setFirstName(signUpRequest.getFirstName());
        user.setLastName(signUpRequest.getLastName());
        user.setPhoneNumber(signUpRequest.getPhoneNumber());
        user.setDateOfBirth(signUpRequest.getDateOfBirth());
        user.setAddress(signUpRequest.getAddress());
        user.setPanNumber(signUpRequest.getPanNumber());
        user.setAadharNumber(signUpRequest.getAadharNumber());
        user.setRole(User.UserRole.CUSTOMER);
        user.setIsActive(true);
        
        return userRepository.save(user);
    }
}