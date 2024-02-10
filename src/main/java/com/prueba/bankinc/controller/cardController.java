package com.prueba.bankinc.controller;

import com.prueba.bankinc.persistency.dto.CardBalanceRequest;
import com.prueba.bankinc.persistency.entity.Card;
import com.prueba.bankinc.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/card")
public class cardController {

    private CardService cardService;
    @Autowired
    private cardController (CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping("/{productId}/number")
    public Card generateCardNumber(@PathVariable("productId") Long productId,
                                   @RequestParam(value = "tipoTarjeta", required = true) String tipoTarjeta) {
        return cardService.guardarCard(productId,tipoTarjeta);
    }
    @PostMapping("/enroll")
    public String activateCard(@RequestBody Map<String, String> payload) {
        Long cardId = Long.valueOf(payload.get("idTarjeta"));
       return cardService.activateCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    public String deletedCard(@PathVariable("cardId")Long idCard){
        cardService.deleteCard(idCard);
        return "tarjeta eliminada";
    }

    @PostMapping("/balance")
    public ResponseEntity<Card> reloadBalance (@RequestBody CardBalanceRequest request){
        Long cardId = request.getCardId();
    Integer balance = request.getBalance();
    Card card = cardService.reloadBalance(cardId,balance);
    if (!card.equals(null)){
        return ResponseEntity.ok(card);
    }
    return ResponseEntity.notFound().build();
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<Integer> getBalance(@PathVariable("cardId") Long cardId) {
        Integer balance = cardService.getBalance(cardId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/card/show/{cardId}")
    public ResponseEntity<?> getCard(@PathVariable("cardId") Long cardId) {
        Optional<Card> cardOptional = null;
        Map<String, Object> response = new HashMap<>();
        try {
            cardOptional = cardService.getCard(cardId);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (cardOptional == null) {
            response.put("mensaje",
                    "La lista: ".concat(cardId.toString().concat(" no existe en nuestra base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Card>>(cardOptional, HttpStatus.OK);

    }
}
