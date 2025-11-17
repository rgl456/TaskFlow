package com.project.TaskFlow.dto;

public record CompanyResponseDTO(
        Long id,
        String name,
        String ownerEmail,
        String location
) {
}
