package com.project.TaskFlow.dto;

import jakarta.validation.constraints.NotBlank;

public record ProjectUpdateRequestDTO(
        @NotBlank String name,
        String description
) {
}
