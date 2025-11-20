package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.ApiResponse;
import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Task created successfully to project " + taskRequestDTO.projectId(),
                        taskService.createTask(taskRequestDTO),
                        LocalDateTime.now()
                ));
    }
}
