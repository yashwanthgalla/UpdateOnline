package com.banking.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposits")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Deposit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "deposit_number", unique = true, nullable = false)
    private String depositNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "deposit_type", nullable = false)
    private DepositType depositType;
    
    @Column(name = "principal_amount", precision = 15, scale = 2, nullable = false)
    private BigDecimal principalAmount;
    
    @Column(name = "maturity_amount", precision = 15, scale = 2)
    private BigDecimal maturityAmount;
    
    @Column(name = "interest_rate", nullable = false)
    private Double interestRate;
    
    @Column(name = "tenure_months", nullable = false)
    private Integer tenureMonths;
    
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;
    
    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate;
    
    @Enumerated(EnumType.STRING)
    private DepositStatus status = DepositStatus.ACTIVE;
    
    @Column(name = "auto_renewal")
    private Boolean autoRenewal = false;
    
    @Column(name = "nomination_details")
    private String nominationDetails;
    
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    
    public enum DepositType {
        FIXED_DEPOSIT, RECURRING_DEPOSIT
    }
    
    public enum DepositStatus {
        ACTIVE, MATURED, CLOSED, PREMATURE_CLOSURE
    }
}