package com.project.TaskFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CompanyRequestDTO(
        @NotBlank String name,
        @Email String ownerEmail,
        @NotBlank String location
) {}
