package com.prueba.bankinc.controller;

import com.prueba.bankinc.persistency.dto.CardBalanceRequest;
import com.prueba.bankinc.persistency.entity.Card;
import com.prueba.bankinc.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/card")
public class cardController {

    private CardService cardService;
    @Autowired
    private cardController (CardService cardService){
        this.cardService = cardService;
    }

    @GetMapping("/{productId}/number")
    public Card generateCardNumber(@PathVariable("productId") String productId,
                                   @RequestParam(value = "tipoTarjeta", required = true) String tipoTarjeta) {
        return cardService.guardarCard(productId,tipoTarjeta);
    }
    @PostMapping("/enroll")
    public String activateCard(@RequestBody Map<String, String> payload) {
        String cardId = payload.get("idTarjeta");
       return cardService.activateCard(cardId);
    }

    @DeleteMapping("/{cardId}")
    public String deletedCard(@PathVariable("cardId")String cardId){
        cardService.deleteCard(cardId);
        return "tarjeta eliminada";
    }

    @PostMapping("/balance")
    public ResponseEntity<Card> reloadBalance (@RequestBody CardBalanceRequest request){
    String cardId = request.getCardId();
    Integer balance = request.getBalance();
    Card card = cardService.reloadBalance(cardId,balance);
    if (!card.equals(null)){
        return ResponseEntity.ok(card);
    }
    return ResponseEntity.notFound().build();
    }

    @GetMapping("/balance/{cardId}")
    public ResponseEntity<Integer> getBalance(@PathVariable("cardId") String cardId) {
        Integer balance = cardService.getBalance(cardId);
        if (balance != null) {
            return ResponseEntity.ok(balance);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
