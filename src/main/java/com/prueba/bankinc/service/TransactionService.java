package com.prueba.bankinc.service;

import com.prueba.bankinc.manejo.ResourceNotFoundException;
import com.prueba.bankinc.persistency.entity.Card;
import com.prueba.bankinc.persistency.entity.Transaction;
import com.prueba.bankinc.persistency.repository.CardRepository;
import com.prueba.bankinc.persistency.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private  final CardRepository cardRepository;
    @Autowired
    public TransactionService(TransactionRepository transactionRepository, CardRepository  cardRepository) {
        this.transactionRepository = transactionRepository;
        this.cardRepository = cardRepository;
    }
    public Transaction purchase(Long idCard, Integer price,TransactionRepository transactionRepository) {
        Optional<Card> cardOptional = Optional.ofNullable(cardRepository.findByidCard(idCard)
                .orElseThrow(() -> new ResourceNotFoundException("Card not found with id: " + idCard)));
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            if (card.getBalance().compareTo(price) >= 0 && card.getActiva()) {
                Transaction transaction = new Transaction();
                transaction.setPrice(price);
                transaction.setAnulated(false);
                card.setBalance(card.getBalance()-transaction.getPrice());
                transaction.setTransactionDate(LocalDateTime.now());
                transaction.setCard(card);
                return transactionRepository.save(transaction);
            }
        }
        return null;
    }
    @Transactional(readOnly = true)
    public Optional<Transaction> getTrasactions(Long transactionId){
        return transactionRepository.findById(transactionId);
    }
    public void anulateTransaction(TransactionAnulationRequest request) {
        Optional<Card> optionalCard = cardRepository.findById(request.getCardId());
        if (optionalCard.isEmpty()) {
            throw new NotFoundException("Card not found with ID: " + request.getCardId());
        }

        Card card = optionalCard.get();
        Optional<Transaction> optionalTransaction = card.getTransactions().stream()
                .filter(transaction -> transaction.getId().equals(request.getTransactionId()))
                .findFirst();
        if (optionalTransaction.isEmpty()) {
            throw new NotFoundException("Transaction not found with ID: " + request.getTransactionId());
        }

        Transaction transaction = optionalTransaction.get();
        if (transaction.isAnulated() || ChronoUnit.HOURS.between(transaction.getTransactionDate(), LocalDateTime.now()) > 24) {
            throw new InvalidTransactionException("Transaction cannot be anulated or is older than 24 hours");
        }

        transaction.setAnulated(true);
        transaction.setPrice(0);
        card.setBalance(card.getBalance() + transaction.getPrice());
        transactionRepository.save(transaction);
    }
}
