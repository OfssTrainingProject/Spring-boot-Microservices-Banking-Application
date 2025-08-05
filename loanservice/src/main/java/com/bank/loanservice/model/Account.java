package com.bank.loanservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;

@Entity
@Data
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AADHAAR_NUMBER")
    private String aadhaarNumber;

    @Column(name = "ACCOUNT_NUMBER", nullable = false, unique = true)
    private String accountNumber;

    private String accountType;
    private String address;

    private BigDecimal balance;
    private String pinCode;
    private String state;

    @Column(name = "USER_ID", nullable = false)
    private Long userId;
}
