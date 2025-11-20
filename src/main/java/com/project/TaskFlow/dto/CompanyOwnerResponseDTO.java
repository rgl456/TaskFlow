package com.project.TaskFlow.dto;

public record CompanyOwnerResponseDTO(
        Long id,
        String name,
        String ownerEmail,
        String location
) {
}
