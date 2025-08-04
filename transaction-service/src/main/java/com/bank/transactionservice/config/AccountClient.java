package com.bank.transactionservice.config;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.bank.common.dto.AccountDTO;

@FeignClient(name="account-service")
public interface AccountClient {
	@GetMapping("/api/v1/account/{accountNumber}")
    Optional<AccountDTO> getAccountByAccountNumber(@PathVariable("accountNumber") String accountNumber);

	
	@PostMapping("/api/v1/account/debit/{accountNumber}")
    void debitAmount(@PathVariable("accountNumber") String accountNumber,
                     @RequestBody BigDecimal amountRequest);

    @PostMapping("/api/v1/account/credit/{accountNumber}")
    void creditAmount(@PathVariable("accountNumber") String accountNumber,
                      @RequestBody BigDecimal amountRequest);
}

