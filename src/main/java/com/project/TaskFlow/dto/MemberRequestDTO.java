package com.project.TaskFlow.dto;

import com.project.TaskFlow.model.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record MemberRequestDTO(
        @NotNull Long userId,
        @NotNull Role role
) {
}
