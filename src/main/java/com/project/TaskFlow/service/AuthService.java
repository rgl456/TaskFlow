package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.*;
import com.project.TaskFlow.model.User;
import jakarta.validation.Valid;

public interface AuthService {
    AuthResponseDTO register(UserRequestDTO requestDTO);
    AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO);
    CompanyTokenResponseDTO switchCompany(CompanySwitchRequestDTO requestDTO);
    Object refresh(RefreshTokenRequestDTO requestDTO);
}
