package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.UserResponseDTO;
import com.project.TaskFlow.dto.UserUpdateRequestDTO;

public interface UserService {
    UserResponseDTO getUserById(Long userId);
    UserResponseDTO getUserById();
    UserResponseDTO updateUser(UserUpdateRequestDTO requestDTO);
    String deleteUserById(Long userId);
}
