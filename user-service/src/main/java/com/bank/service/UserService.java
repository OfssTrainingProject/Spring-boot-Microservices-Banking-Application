package com.bank.service;

import java.util.Optional;

import com.bank.common.dto.UserDTO;
import com.bank.model.User;

public interface UserService {
	public UserDTO createUser(UserDTO userDTO) ;

    public Optional<UserDTO> getUser(Long id) ;
}
