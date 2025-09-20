package com.banking.app.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Card {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "card_number", unique = true, nullable = false)
    private String cardNumber;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "card_type", nullable = false)
    private CardType cardType;
    
    @Column(name = "card_holder_name", nullable = false)
    private String cardHolderName;
    
    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;
    
    @Column(name = "credit_limit", precision = 15, scale = 2)
    private BigDecimal creditLimit;
    
    @Column(name = "available_limit", precision = 15, scale = 2)
    private BigDecimal availableLimit;
    
    @Column(name = "outstanding_balance", precision = 15, scale = 2)
    private BigDecimal outstandingBalance = BigDecimal.ZERO;
    
    @Column(name = "minimum_payment", precision = 15, scale = 2)
    private BigDecimal minimumPayment = BigDecimal.ZERO;
    
    @Column(name = "payment_due_date")
    private LocalDate paymentDueDate;
    
    @Column(name = "annual_fee", precision = 15, scale = 2)
    private BigDecimal annualFee;
    
    @Column(name = "interest_rate")
    private Double interestRate;
    
    @Column(name = "is_active")
    private Boolean isActive = true;
    
    @Column(name = "is_blocked")
    private Boolean isBlocked = false;
    
    @Enumerated(EnumType.STRING)
    private CardStatus status = CardStatus.APPLIED;
    
    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    public enum CardType {
        DEBIT, CREDIT
    }
    
    public enum CardStatus {
        APPLIED, APPROVED, REJECTED, ACTIVE, BLOCKED, EXPIRED, CANCELLED
    }
}