package com.prueba.bankinc.persistency.repository;

import com.prueba.bankinc.persistency.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CardRepository extends JpaRepository <Card,String> {
    Optional<Card> findByidTarjeta(String idTarjeta);
    void deleteByidTarjeta(String idTarjeta);
}
