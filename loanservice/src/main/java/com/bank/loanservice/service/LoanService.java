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
    import java.util.Random;
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
            Random random = new Random();
            int number = 10000000 + random.nextInt(90000000); // Generates 8 digit number (from 10000000 to 99999999)
            return "LN" + number;
        }

        public List<Loan> getLoansByAccountId(Long accountId) {
            return loanRepository.findByAccountId(accountId);
        }

        public String getNextEMIDetailsByLoanNumber(String loanNumber) {
            Loan loan = loanRepository.findByLoanNumber(loanNumber)
                    .orElseThrow(() -> new RuntimeException("Loan not found"));

            return String.format("%.2f", loan.getEmiAmount()) + " due on " + LocalDate.now().plusMonths(1);

        }


        public String repayLoan(String loanNumber, String accountNumber) {
            Optional<Loan> loanOpt = loanRepository.findByLoanNumber(loanNumber);
            if (loanOpt.isEmpty()) {
                return "Loan not found.";
            }
            Loan loan = loanOpt.get();

            Optional<Account> accountOpt = accountRepository.findByAccountNumber(accountNumber);
            if (accountOpt.isEmpty()) {
                return "Account not found.";
            }

            Account account = accountOpt.get();

            if (account.getBalance().doubleValue() < loan.getEmiAmount()) {
                return "Insufficient balance.";
            }

            // Deduct balance and update account
            account.setBalance(account.getBalance().subtract(new java.math.BigDecimal(loan.getEmiAmount())));
            accountRepository.save(account);

            // Update loan remaining amount
            loan.setRemainingAmount(loan.getRemainingAmount() - loan.getEmiAmount());
            if (loan.getRemainingAmount() <= 0) {
                loan.setStatus("Pending");
                loan.setRemainingAmount(0.0);
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
