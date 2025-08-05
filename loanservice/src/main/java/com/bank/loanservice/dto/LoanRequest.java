package com.bank.loanservice.dto;

import lombok.Data;

@Data
public class LoanRequest {
    private Long userId;
    private Double amount;
    private Integer termMonths;
    private String loanType; // personal, motor, home
    private String accountNumber;
}
