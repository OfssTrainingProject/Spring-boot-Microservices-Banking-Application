package com.bank.loanservice.util;

public class EMICalculator {
    public static double calculateEMI(double principal, double annualRate, int termMonths) {
        double monthlyRate = annualRate / 12 / 100;
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, termMonths)) /
                (Math.pow(1 + monthlyRate, termMonths) - 1);
    }
}
