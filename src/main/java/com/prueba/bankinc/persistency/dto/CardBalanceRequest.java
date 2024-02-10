package com.prueba.bankinc.persistency.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardBalanceRequest {
    private Long cardId;
    private Integer balance;
}
