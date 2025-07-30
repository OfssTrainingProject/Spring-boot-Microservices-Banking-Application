package com.bank.service;

import com.bank.common.dto.UserDTO;
import com.bank.mapper.UserMapper;
import com.bank.model.User;
import com.bank.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public UserDTO createUser(UserDTO userDTO) {
        User user = userMapper.toEntity(userDTO);
        User savedUser = userRepository.save(user);
        return userMapper.toDTO(savedUser);
    }

    public Optional<UserDTO> getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDTO);
    }
}