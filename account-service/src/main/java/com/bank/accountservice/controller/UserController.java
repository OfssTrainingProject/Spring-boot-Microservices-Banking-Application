package com.bank.accountservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bank.accountservice.model.User;
import com.bank.accountservice.service.UserService;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*") // For local testing
public class UserController {

    @Autowired
    private UserService userService;

    // Register
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if (userService.emailExists(user.getEmail())) {
            return ResponseEntity.status(409).body("Email already registered");
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok("Registration successful. Your account number is: " + savedUser.getAccountNumber());
    }

    // Login
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody User user) {
        if (userService.validateLogin(user.getEmail(), user.getPassword())) {
            return ResponseEntity.ok("Login successful");
        }
        return ResponseEntity.status(401).body("Invalid email or password");
    }
}
