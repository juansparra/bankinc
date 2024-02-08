package com.prueba.bankinc.service;

import com.prueba.bankinc.persistency.repository.cardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cardService {


private final cardRepository CardRepository;
    @Autowired
    public cardService(cardRepository cardRepository) {
       this.CardRepository = cardRepository;
    }

        public String generarNumeroCard(String productId) {
            String cardNumber = productId;
            for (int i = 6; i < 16; i++) {
                cardNumber += String.format("%01d", (int) (Math.random() * 10));
            }
            return cardNumber;
        }
    }
