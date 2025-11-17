package com.project.TaskFlow.dto;

import java.time.LocalDateTime;

public record ApiResponse(
        String message,
        Object data,
        LocalDateTime timestamp
) {
}
