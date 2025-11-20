package com.project.TaskFlow.dto;

import java.util.List;
import java.util.Set;

public record ProjectResponseDTO(
        Long id,
        String name,
        String description,
        Long companyId,
        Long managerId,
        Set<ProjectMemberResponseDTO> members
) {
}
