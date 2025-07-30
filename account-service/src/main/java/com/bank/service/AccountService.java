package com.bank.service;

import com.bank.common.dto.AccountDTO;

public interface AccountService {
	public AccountDTO createAccount (AccountDTO dto) ;
	public AccountDTO getAccount(Long id);
}
