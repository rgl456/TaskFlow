package com.project.TaskFlow.dto;

import java.time.LocalDateTime;

public record CompanyUserResponseDTO(Long companyId, String companyName, String Location, String userRole, LocalDateTime joinedAt){
}
