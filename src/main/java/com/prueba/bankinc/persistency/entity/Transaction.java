package com.prueba.bankinc.persistency.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Data
@Getter
@Setter
@Table(name = "transaction")
public class Transaction {
    @Id
    @GeneratedValue
    private String id;
    @ManyToOne
    private Card fuenteCard;
    @ManyToOne
    private Card destinoCard;
    private double monto;
    private boolean anulada;
    private LocalDateTime fecha;
}
