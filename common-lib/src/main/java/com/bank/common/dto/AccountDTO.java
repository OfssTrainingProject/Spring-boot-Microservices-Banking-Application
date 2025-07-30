package com.bank.common.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private Long id;
    private Long userId;
    private BigDecimal balance;
    private String accountType;
    private String accountNumber;

}
