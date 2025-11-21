package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.ApiResponse;
import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task created successfully to project " + taskRequestDTO.projectId(),
                        taskService.createTask(taskRequestDTO),
                        LocalDateTime.now()
                ));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Long taskId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task fetched successfully!",
                        taskService.getTaskById(taskId),
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse> updateTaskById(@PathVariable Long taskId, @Valid @RequestBody TaskRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task updated successfully!",
                        taskService.updateTaskById(taskId, requestDTO),
                        LocalDateTime.now()
                ));
    }

    @DeleteMapping("/{taskId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> deleteTaskById(@PathVariable Long taskId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task deleted successfully!",
                        taskService.deleteTaskById(taskId),
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/{taskId}/member/{memberId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'OWNER', 'MANAGER')")
    public ResponseEntity<ApiResponse> assignTaskToMember(@PathVariable Long taskId, @PathVariable Long memberId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task "+ taskId +" assigned successfully! to the member "+ memberId,
                        taskService.assignTaskToMember(taskId, memberId),
                        LocalDateTime.now()
                ));
    }

}
