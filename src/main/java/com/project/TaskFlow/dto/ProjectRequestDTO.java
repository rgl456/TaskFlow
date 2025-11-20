package com.project.TaskFlow.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProjectRequestDTO (
        @NotBlank String name,
        String description,
        @NotNull Long companyId,
        @NotNull Long userId
){
}
