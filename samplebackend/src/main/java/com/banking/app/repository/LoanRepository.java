package com.banking.app.repository;

import com.banking.app.entity.Loan;
import com.banking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
    
    Optional<Loan> findByLoanNumber(String loanNumber);
    
    List<Loan> findByUser(User user);
    
    List<Loan> findByUserAndStatus(User user, Loan.LoanStatus status);
    
    List<Loan> findByStatus(Loan.LoanStatus status);
    
    List<Loan> findByLoanType(Loan.LoanType loanType);
    
    boolean existsByLoanNumber(String loanNumber);
}