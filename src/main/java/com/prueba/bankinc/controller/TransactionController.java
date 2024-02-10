package com.prueba.bankinc.controller;

import com.prueba.bankinc.persistency.entity.Card;
import com.prueba.bankinc.persistency.entity.Transaction;
import com.prueba.bankinc.persistency.repository.TransactionRepository;
import com.prueba.bankinc.service.TransactionService;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private final TransactionService transactionService;
    private final TransactionRepository transactionRepository;

    public TransactionController (TransactionService transactionService, TransactionRepository transactionRepository){
        this.transactionService = transactionService;
        this.transactionRepository = transactionRepository;
    }

    @PostMapping("/purchase")
    public ResponseEntity<Transaction> purchase(@RequestBody Map<String, String> request) {
        Long cardId = Long.valueOf(request.get("cardId"));
        Integer price = Integer.valueOf(request.get("price"));
        Transaction transaction = transactionService.purchase(cardId, price,transactionRepository);
        if (transaction != null) {
            return ResponseEntity.ok(transaction);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/{transactionId}")
    public ResponseEntity<?> getTrasactions(@PathVariable("transactionId") Long transactionId) {
        Optional<Transaction> transactionOptional = null;
        Map<String, Object> response = new HashMap<>();
        try {
            transactionOptional = transactionService.getTrasactions(transactionId);
        } catch (DataAccessException e) {
            response.put("mensaje", "Error al realizar la consulta en la base de datos");
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        if (transactionOptional == null) {
            response.put("mensaje",
                    "La lista: ".concat(transactionId.toString().concat(" no existe en nuestra base de datos")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Optional<Transaction>>(transactionOptional, HttpStatus.OK);

    }
}
