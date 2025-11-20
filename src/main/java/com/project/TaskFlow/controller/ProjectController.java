package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.AddMemberRequestDTO;
import com.project.TaskFlow.dto.ApiResponse;
import com.project.TaskFlow.dto.ProjectRequestDTO;
import com.project.TaskFlow.dto.ProjectUpdateRequestDTO;
import com.project.TaskFlow.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> createProject(@Valid @RequestBody ProjectRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                   "Project created successfully!",
                   projectService.createProject(requestDTO),
                   LocalDateTime.now()
                ));
    }

    @GetMapping("/{projectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable Long projectId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Project details fetched successfully!",
                        projectService.getProjectById(projectId),
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/{projectId}/members")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> addMemberToProject(@PathVariable Long projectId, @Valid @RequestBody AddMemberRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "User assigned successfully to project " + projectId,
                        projectService.addMemberToProject(projectId, requestDTO),
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/{projectId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectUpdateRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Project details updated successfully!",
                        projectService.updateProject(projectId, requestDTO),
                        LocalDateTime.now()
                ));
    }



}
