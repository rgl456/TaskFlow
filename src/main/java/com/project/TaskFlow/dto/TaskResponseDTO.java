package com.project.TaskFlow.dto;

import java.time.LocalDateTime;

public record TaskResponseDTO(
        Long taskId,
        String title,
        String description,
        String status,
        LocalDateTime dueDate,
        Long assignedTo,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        Long projectId,
        Long managerId,
        Long companyId
) {
}
