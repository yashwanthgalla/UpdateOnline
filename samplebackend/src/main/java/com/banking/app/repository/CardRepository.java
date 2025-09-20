package com.banking.app.repository;

import com.banking.app.entity.Card;
import com.banking.app.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    
    Optional<Card> findByCardNumber(String cardNumber);
    
    List<Card> findByUser(User user);
    
    List<Card> findByUserAndIsActive(User user, Boolean isActive);
    
    List<Card> findByCardType(Card.CardType cardType);
    
    List<Card> findByStatus(Card.CardStatus status);
    
    boolean existsByCardNumber(String cardNumber);
}