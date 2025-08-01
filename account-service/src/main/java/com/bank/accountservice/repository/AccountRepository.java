package com.bank.accountservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountservice.model.Account;

import com.bank.accountservice.model.Account.AccountType;



public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findByUserId(Long userId);
    void deleteByUserId(Long userId);
    void deleteById(Long id);
    boolean existsByUserIdAndAccountType(Long userId, AccountType accountType);
    boolean existsByAccountNumber(String accountNumber);
}
