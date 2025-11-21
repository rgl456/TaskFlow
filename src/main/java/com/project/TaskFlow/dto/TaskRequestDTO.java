package com.project.TaskFlow.dto;

import com.project.TaskFlow.model.Priority;
import com.project.TaskFlow.model.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record TaskRequestDTO(
        @NotNull Long projectId,
        @NotBlank String title,
        String description,
        @NotNull Status status,
        Priority priority,
        LocalDateTime dueDate
) {
}
