package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.TaskRequestDTO;
import jakarta.validation.Valid;

public interface TaskService {
    Object createTask(TaskRequestDTO taskRequestDTO);
}
