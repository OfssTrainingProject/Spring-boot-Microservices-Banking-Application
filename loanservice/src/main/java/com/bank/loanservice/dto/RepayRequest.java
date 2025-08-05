package com.bank.loanservice.dto;

import lombok.Data;

@Data
public class RepayRequest {
    private String accountNumber;
    private Double amount;
}
