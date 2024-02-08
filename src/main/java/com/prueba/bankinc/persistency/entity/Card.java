package com.prueba.bankinc.persistency.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@Table(name = "card")
public class Card {
    @Id
    private Long id;
    @Column(name = "tipo_tarjeta")
    private String tipoTarjeta;
    private Integer balance;
    @Column(name = "tipo_de_divisa")
    private String tipoDeDivisa;
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Column(name = "apellido_usuario")
    private String apellidoUsuario;
    private Boolean activa;
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    @OneToMany(mappedBy = "fuenteCard")
    private Set<Transaction> transactionsSalientes = new HashSet<>();
    @OneToMany(mappedBy = "destinoCard")
    private Set<Transaction> transactionsEntrantes = new HashSet<>();

}
