package com.project.TaskFlow.dto;

import java.util.List;

public record UserResponseDTO(Long userId, String name, String email, List<CompanyUserResponseDTO> companies) {
}
