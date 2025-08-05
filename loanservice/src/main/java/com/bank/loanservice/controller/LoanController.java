package com.bank.loanservice.controller;

import com.bank.loanservice.model.Loan;
import com.bank.loanservice.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
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

    @GetMapping("/emi/{loanNumber}")
    public String getNextEMI(@PathVariable String loanNumber) {
        return loanService.getNextEMIDetailsByLoanNumber(loanNumber);
    }


    @PutMapping("/repay/{loanNumber}/{accountNumber}")
    public String repayLoan(@PathVariable String loanNumber, @PathVariable String accountNumber) {
        return loanService.repayLoan(loanNumber, accountNumber);
    }

}
