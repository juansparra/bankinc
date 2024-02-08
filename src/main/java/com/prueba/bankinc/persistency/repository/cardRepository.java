package com.prueba.bankinc.persistency.repository;

import com.prueba.bankinc.persistency.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface cardRepository extends JpaRepository <Card,Long> {
}
