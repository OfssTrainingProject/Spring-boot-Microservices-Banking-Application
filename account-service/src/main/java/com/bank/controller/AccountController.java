package com.bank.controller;

import com.bank.common.dto.AccountDTO;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO accountDTO) {
        AccountDTO createdAccount = accountService.createAccount(accountDTO);
        return ResponseEntity.ok(createdAccount);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable("id") Long id) {
        return ResponseEntity.ok(accountService.getAccount(id));
    }
}