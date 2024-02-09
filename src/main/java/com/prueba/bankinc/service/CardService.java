package com.prueba.bankinc.service;

import com.prueba.bankinc.persistency.entity.Card;
import com.prueba.bankinc.persistency.entity.Transaction;
import com.prueba.bankinc.persistency.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class CardService {


    private final CardRepository cardRepository;

    @Autowired
    public CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public String generarNumeroCard(String productId) {
        String cardNumber = productId;
        for (int i = 6; i < 16; i++) {
            cardNumber += String.format("%01d", (int) (Math.random() * 10));
        }
        return cardNumber;
    }

    private LocalDate calculateExpirationDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusYears(3);
    }

    public Card guardarCard(String productId, String tipoTarjeta) {
        Card card = new Card();
        card.setIdProducto(productId);
        card.setIdTarjeta(generarNumeroCard(productId));
        card.setNombreUsuario("juan");
        card.setApellidoUsuario("parra");
        card.setTipoTarjeta(tipoTarjeta);
        card.setBalance(0);
        card.setTipoDeDivisa("DOLAR");
        card.setActiva(false);
        card.setFechaVencimiento(calculateExpirationDate());
        return cardRepository.save(card);
    }

    public String activateCard(String idTarjeta) {
        Optional<Card> CardOptional = cardRepository.findByidTarjeta(idTarjeta);
        if (CardOptional.isPresent()) {
            Card card = CardOptional.get();
            if (!card.getActiva()) {
                card.setActiva(true);
                cardRepository.save(card);
                return "La tarjeta fue activada correctamente";
            } else {
                return "La tarjeta ya esta activa";
            }
        } else {
            return "Tarjeta no encontrada";
        }
    }

    @Transactional
    public void deleteCard(String idTarjeta) {
        cardRepository.deleteByidTarjeta(idTarjeta);
    }

    public Card reloadBalance(String cardId, Integer balance) {
        Optional<Card> cardOptional = cardRepository.findByidTarjeta(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            card.setBalance(balance);
            return cardRepository.save(card);
        }
        return null;
    }

    public Integer getBalance(String cardId) {
        Optional<Card> cardOptional = cardRepository.findByidTarjeta(cardId);
        if (cardOptional.isPresent()) {
            return cardOptional.get().getBalance();
        }
        return null;
    }


}