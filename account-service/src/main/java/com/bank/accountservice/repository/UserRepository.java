package com.bank.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.accountservice.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
