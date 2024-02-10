package com.prueba.bankinc.service;

import com.prueba.bankinc.persistency.entity.Card;
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

    public Long generarNumeroCard(Long productId) {
        // Convertir el productId a String
        String productIdStr = String.valueOf(productId);

        // Asegurarse de que productId tiene al menos 6 dígitos
        if (productIdStr.length() < 6) {
            throw new IllegalArgumentException("El productId debe tener al menos 6 dígitos");
        }

        // Usar los primeros 6 dígitos de productId
        String cardNumberStr = productIdStr.substring(0, 6);

        // Generar los 10 dígitos aleatorios restantes
        for (int i = 0; i < 10; i++) {
            cardNumberStr += (int) (Math.random() * 10);
        }

        // Convertir el número de tarjeta generado a Long
        Long cardNumber = Long.parseLong(cardNumberStr);

        return cardNumber;
    }



    private LocalDate calculateExpirationDate() {
        LocalDate currentDate = LocalDate.now();
        return currentDate.plusYears(3);
    }

    public Card guardarCard(Long productId, String tipoTarjeta) {
        Card card = new Card();
        card.setIdProducto(productId);
        card.setIdCard(generarNumeroCard(productId));
        card.setNombreUsuario("juan");
        card.setApellidoUsuario("parra");
        card.setTipoTarjeta(tipoTarjeta);
        card.setBalance(0);
        card.setTipoDeDivisa("DOLAR");
        card.setActiva(false);
        card.setFechaVencimiento(calculateExpirationDate());
        return cardRepository.save(card);
    }

    public String activateCard(Long idTarjeta) {
        Optional<Card> CardOptional = cardRepository.findByidCard(idTarjeta);
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
    public void deleteCard(Long idCard) {
        cardRepository.deleteByidCard(idCard);
    }

    public Card reloadBalance(Long cardId, Integer balance) {
        Optional<Card> cardOptional = cardRepository.findByidCard(cardId);
        if (cardOptional.isPresent()) {
            Card card = cardOptional.get();
            card.setBalance(balance);
            return cardRepository.save(card);
        }
        return null;
    }

    public Integer getBalance(Long cardId) {
        Optional<Card> cardOptional = cardRepository.findByidCard(cardId);
        if (cardOptional.isPresent()) {
            return cardOptional.get().getBalance();
        }
        return null;
    }

    @Transactional(readOnly = true)
    public Optional<Card> getCard(Long cardId){
        return cardRepository.findByidCard(cardId);
    }


}