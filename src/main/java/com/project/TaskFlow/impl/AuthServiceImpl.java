package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.AuthResponseDTO;
import com.project.TaskFlow.dto.UserRequestDTO;
import com.project.TaskFlow.mapper.UserMapper;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.service.AuthService;

public class AuthServiceImpl implements AuthService {

    @Override
    public AuthResponseDTO register(UserRequestDTO requestDTO) {
        User user = UserMapper.dtoToEntity(requestDTO);
        return null;
    }
}
