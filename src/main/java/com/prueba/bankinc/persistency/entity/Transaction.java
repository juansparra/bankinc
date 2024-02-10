package com.prueba.bankinc.persistency.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    private Long id;
    private Integer price;
    @Column(name = "is_anulated")
    private boolean isAnulated;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_card", nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Card card;
}
