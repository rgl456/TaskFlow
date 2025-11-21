package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.dto.TaskResponseDTO;
import jakarta.validation.Valid;

public interface TaskService {
    TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO);
    TaskResponseDTO getTaskById(Long taskId);
}
