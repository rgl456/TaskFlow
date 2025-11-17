package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.AuthResponseDTO;
import com.project.TaskFlow.dto.UserRequestDTO;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponseDTO register(UserRequestDTO requestDTO);
}
