package com.project.TaskFlow.dto;

import com.project.TaskFlow.model.Role;

public record CompanyMembershipResponseDTO(
        Long id,
        Long companyId,
        Long UserId,
        Role role
) {
}
