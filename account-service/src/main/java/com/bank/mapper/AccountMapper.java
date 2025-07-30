
package com.bank.mapper;

import com.bank.common.dto.AccountDTO;
import com.bank.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account) {
        return new AccountDTO(
                account.getId(),
                account.getUserId(),
                account.getBalance(),
                account.getAccountType(),
                account.getAccountNumber()
        );
    }

    public Account toEntity(AccountDTO accountDTO) {
        Account account = new Account();
        account.setId(accountDTO.getId());
        account.setUserId(accountDTO.getUserId());
        account.setBalance(accountDTO.getBalance());
        account.setAccountType(accountDTO.getAccountType());
        account.setAccountNumber(accountDTO.getAccountNumber());
        return account;
    }
}