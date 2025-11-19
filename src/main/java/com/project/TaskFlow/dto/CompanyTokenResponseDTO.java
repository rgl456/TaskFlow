package com.project.TaskFlow.dto;

import java.time.LocalDateTime;

public record CompanyTokenResponseDTO(
        Long companyId,
        Long userId,
        String role,
        String accessToken,
        String expireAt
) {
}
