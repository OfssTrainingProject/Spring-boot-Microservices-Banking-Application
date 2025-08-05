package com.bank.loanservice.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String loanNumber;
    private Double amount;
    private Double interestRate;
    private Integer termMonths;
    private Long accountId;
    private LocalDate issueDate;
    private Double emiAmount;
    private Double remainingAmount;
    private String loanType; // e.g., Personal, Home
    private String status;   // Active, Pending
}
