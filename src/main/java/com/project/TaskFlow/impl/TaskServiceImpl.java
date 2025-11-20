package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.repository.TaskRepository;
import com.project.TaskFlow.service.TaskService;
import org.springframework.stereotype.Service;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Object createTask(TaskRequestDTO taskRequestDTO) {
        return null;
    }
}
