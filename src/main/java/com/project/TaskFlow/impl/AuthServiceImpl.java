package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.AuthResponseDTO;
import com.project.TaskFlow.dto.UserRequestDTO;
import com.project.TaskFlow.mapper.UserMapper;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.service.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;

    public AuthServiceImpl(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(UserRequestDTO requestDTO) {
        User user = UserMapper.dtoToEntity(requestDTO);
        user.setPassword(passwordEncoder.encode(requestDTO.password()));

        return user;
    }
}
