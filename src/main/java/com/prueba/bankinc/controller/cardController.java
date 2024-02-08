package com.prueba.bankinc.controller;

import com.prueba.bankinc.service.cardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/card")
public class cardController {

    private cardService CardService;
    @Autowired
    private cardController (cardService cardService){
        this.CardService = cardService;
    }

    @GetMapping("/{productId}/number")
    public String generateCardNumber(@PathVariable("productId") String productId) {
        return CardService.generarNumeroCard(productId);
    }
}
