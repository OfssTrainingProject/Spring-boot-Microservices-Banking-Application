package com.bank.loanservice.service;

import com.bank.loanservice.model.Account;
import com.bank.loanservice.model.Loan;
import com.bank.loanservice.repository.AccountRepository;
import com.bank.loanservice.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Loan issueLoan(Loan loan) {
        loan.setLoanNumber(generateLoanNumber());
        loan.setIssueDate(LocalDate.now());
        loan.setInterestRate(getInterestRate(loan.getLoanType()));
        loan.setEmiAmount(calculateEMI(loan.getAmount(), loan.getInterestRate(), loan.getTermMonths()));
        loan.setRemainingAmount(loan.getAmount());
        loan.setStatus("Active");
        return loanRepository.save(loan);
    }

    private String generateLoanNumber() {
        int randomNumber = (int) (Math.random() * 1_0000_0000); // 8 digits
        return "LN" + String.format("%08d", randomNumber);
    }

    public List<Loan> getLoansByAccountId(Long accountId) {
        return loanRepository.findByAccountId(accountId);
    }

    public String getNextEMIDetails(Long loanId) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        return "Next EMI for Loan " + loan.getLoanNumber() + " is ₹" + loan.getEmiAmount() + " due on " + LocalDate.now().plusMonths(1);
    }

    public String repayLoan(Long loanId, String accountNumber) {
        Loan loan = loanRepository.findById(loanId).orElseThrow();
        Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);

        if (accountOpt.isEmpty()) return "Account not found.";

        Account account = accountOpt.get();

        if (account.getBalance().doubleValue() < loan.getEmiAmount()) {
            return "Insufficient balance.";
        }

        // Deduct balance and update account
        account.setBalance(account.getBalance().subtract(new java.math.BigDecimal(loan.getEmiAmount())));
        accountRepository.save(account);

        // Update loan
        loan.setRemainingAmount(loan.getRemainingAmount() - loan.getEmiAmount());
        if (loan.getRemainingAmount() <= 0) {
            loan.setStatus("Pending");
        }
        loanRepository.save(loan);
        return "EMI paid. Remaining Loan Amount: ₹" + loan.getRemainingAmount();
    }

    private double getInterestRate(String loanType) {
        return switch (loanType) {
            case "Personal" -> 8.0;
            case "Home" -> 8.25;
            case "Motor" -> 9.0;
            default -> 10.0;
        };
    }

    private double calculateEMI(double principal, double rate, int months) {
        rate = rate / (12 * 100);
        return (principal * rate * Math.pow(1 + rate, months)) / (Math.pow(1 + rate, months) - 1);
    }
}
