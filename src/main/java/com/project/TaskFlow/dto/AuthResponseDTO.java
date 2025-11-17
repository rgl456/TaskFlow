package com.project.TaskFlow.dto;

import java.time.LocalDateTime;

public record AuthResponseDTO(
        String accessToken,
        String refreshToken,
        String email,
        String role,
        LocalDateTime expireAt
) {

}
