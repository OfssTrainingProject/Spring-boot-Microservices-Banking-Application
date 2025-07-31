package com.useraccountservice.service;

import com.useraccountservice.model.User;
import com.useraccountservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private final Random random = new Random();

    // Generate unique 12-digit account number
    public String generateUniqueAccountNumber() {
        String accountNumber;
        do {
            accountNumber = String.format("%012d", Math.abs(random.nextLong() % 1_000_000_000_000L));
        } while (userRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }

    // Register user
    public User registerUser(User user) {
        user.setAccountNumber(generateUniqueAccountNumber());
        return userRepository.save(user);
    }

    // Check if email exists
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    // Login user
    public boolean validateLogin(String email, String password) {
        User user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
}
