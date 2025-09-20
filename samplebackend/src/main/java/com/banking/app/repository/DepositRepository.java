package com.banking.app.repository;

import com.banking.app.entity.Deposit;
import com.banking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepositRepository extends JpaRepository<Deposit, Long> {
    
    Optional<Deposit> findByDepositNumber(String depositNumber);
    
    List<Deposit> findByUser(User user);
    
    List<Deposit> findByUserAndStatus(User user, Deposit.DepositStatus status);
    
    List<Deposit> findByStatus(Deposit.DepositStatus status);
    
    List<Deposit> findByDepositType(Deposit.DepositType depositType);
    
    boolean existsByDepositNumber(String depositNumber);
}