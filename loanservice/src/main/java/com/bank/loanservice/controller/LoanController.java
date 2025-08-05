package com.bank.loanservice.controller;

import com.bank.loanservice.model.Loan;
import com.bank.loanservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/issue")
    public Loan issueLoan(@RequestBody Loan loan) {
        return loanService.issueLoan(loan);
    }

    @GetMapping("/account/{accountId}")
    public List<Loan> getLoans(@PathVariable Long accountId) {
        return loanService.getLoansByAccountId(accountId);
    }

    @GetMapping("/emi/{loanId}")
    public String getNextEMI(@PathVariable Long loanId) {
        return loanService.getNextEMIDetails(loanId);
    }

    @PutMapping("/repay/{loanId}/{accountNumber}")
    public String repayLoan(@PathVariable Long loanId, @PathVariable String accountNumber) {
        return loanService.repayLoan(loanId, accountNumber);
    }
}
