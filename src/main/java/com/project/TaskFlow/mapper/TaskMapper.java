package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.dto.TaskResponseDTO;
import com.project.TaskFlow.model.Task;

public class TaskMapper {

    public static TaskResponseDTO entityToResponse(Task task){
        return new TaskResponseDTO(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getDueDate(),
                task.getAssignedTo().getId(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.getProject().getId(),
                task.getProject().getManager().getId(),
                task.getProject().getCompany().getId()
        );
    }

    public static Task dtoToEntity(TaskRequestDTO taskRequestDTO){
        Task task = new Task();
        task.setTitle(taskRequestDTO.title());
        task.setDescription(taskRequestDTO.description());
        task.setStatus(taskRequestDTO.status());
        task.setPriority(taskRequestDTO.priority());
        task.setDueDate(taskRequestDTO.dueDate());
        return task;
    }

}
