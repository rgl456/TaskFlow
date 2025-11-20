package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.ProjectMemberResponseDTO;
import com.project.TaskFlow.dto.ProjectResponseDTO;
import com.project.TaskFlow.model.Project;

import java.util.stream.Collectors;

public class ProjectMapper {

    public static ProjectResponseDTO mapToProjectResponseDTO(Project project) {
        return new ProjectResponseDTO(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCompany().getId(),
                project.getManager().getId(),
                project.getMembers().stream().map(m ->
                        new ProjectMemberResponseDTO(
                                m.getId(),
                                m.getName()
                        )).collect(Collectors.toSet())
        );
    }
}
