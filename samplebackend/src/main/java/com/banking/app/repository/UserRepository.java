package com.banking.app.repository;

import com.banking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
    
    boolean existsByPanNumber(String panNumber);
    
    boolean existsByAadharNumber(String aadharNumber);
    
    Optional<User> findByPhoneNumber(String phoneNumber);
}