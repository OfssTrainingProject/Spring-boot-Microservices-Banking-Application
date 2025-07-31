package com.useraccountservice.repository;

import com.useraccountservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByAccountNumber(String accountNumber);
    boolean existsByEmail(String email);
    User findByEmail(String email);
}
