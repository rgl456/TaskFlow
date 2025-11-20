package com.project.TaskFlow.dto;

import jakarta.validation.constraints.NotBlank;

public record RefreshTokenRequestDTO(@NotBlank String refreshToken, Long companyId) {
}
