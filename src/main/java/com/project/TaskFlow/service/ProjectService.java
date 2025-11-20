package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.AddMemberRequestDTO;
import com.project.TaskFlow.dto.ProjectRequestDTO;
import com.project.TaskFlow.dto.ProjectResponseDTO;
import com.project.TaskFlow.dto.ProjectUpdateRequestDTO;
import jakarta.validation.Valid;

public interface ProjectService {
    ProjectResponseDTO createProject(ProjectRequestDTO requestDTO);
    ProjectResponseDTO getProjectById(Long projectId);
    ProjectResponseDTO addMemberToProject(Long projectId, AddMemberRequestDTO requestDTO);
    ProjectResponseDTO updateProject(Long projectId, ProjectUpdateRequestDTO requestDTO);
}
