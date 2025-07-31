
package com.bank.service;

import com.bank.common.dto.AccountDTO;
import com.bank.mapper.AccountMapper;
import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AccountServiceImpl implements AccountService{

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private RestTemplate restTemplate;

    public AccountDTO createAccount(AccountDTO accountDTO) {
        // Validate userId by calling user-service via Eureka
        String userServiceUrl = "http://user-service/user/" + accountDTO.getUserId();
        try {
            restTemplate.getForObject(userServiceUrl, Object.class);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid userId: " + accountDTO.getUserId());
        }

        Account account = accountMapper.toEntity(accountDTO);
        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }

    public AccountDTO getAccount(Long id) {
        return accountRepository.findById(id)
                .map(accountMapper::toDTO)
                .orElseThrow(() -> new IllegalArgumentException("Account not found: " + id));
    }
}
