package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.TaskRequestDTO;
import com.project.TaskFlow.dto.TaskResponseDTO;
import com.project.TaskFlow.mapper.TaskMapper;
import com.project.TaskFlow.model.*;
import com.project.TaskFlow.repository.*;
import com.project.TaskFlow.service.TaskService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service

public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, CompanyRepository companyRepository, UserRepository userRepository, CompanyMembershipRepository companyMembershipRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.companyMembershipRepository = companyMembershipRepository;
    }

    @Override
    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(taskRequestDTO.projectId())
                .orElseThrow(() -> new RuntimeException("Project not found with id " + taskRequestDTO.projectId()));

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(project.getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        if (membership.getRole() == Role.MEMBER) {
            throw new RuntimeException("Access Denied: Only Managers or Owners can get project details.");
        }

        Task task = TaskMapper.dtoToEntity(taskRequestDTO);
        task.setProject(project);
        Task savedTask = taskRepository.save(task);

        return TaskMapper.entityToResponse(savedTask);
    }

    @Override
    public TaskResponseDTO getTaskById(Long taskId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task is not found!"));


        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(task.getProject().getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        boolean isAssignee = task.getAssignedTo().getId().equals(currentUser.getId());
        boolean isProjectManager = task.getProject().getManager().getId().equals(currentUser.getId());
        boolean isCompanyAdmin = (membership.getRole() == Role.OWNER || membership.getRole() == Role.ADMIN);

        if (!isAssignee && !isProjectManager && !isCompanyAdmin) {
            throw new RuntimeException("Access Denied: You do not have permission to view this task.");
        }

        return TaskMapper.entityToResponse(task);
    }

    @Override
    public TaskResponseDTO updateTaskById(Long taskId, TaskRequestDTO taskRequestDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task is not found!"));

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(task.getProject().getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        boolean isAssignee = task.getAssignedTo().getId().equals(currentUser.getId());
        boolean isProjectManager = task.getProject().getManager().getId().equals(currentUser.getId());
        boolean isCompanyAdmin = (membership.getRole() == Role.OWNER || membership.getRole() == Role.ADMIN);

        if (!isAssignee && !isProjectManager && !isCompanyAdmin) {
            throw new RuntimeException("Access Denied: You do not have permission to view this task.");
        }

        task.setTitle(taskRequestDTO.title());
        task.setDescription(taskRequestDTO.description());
        task.setStatus(taskRequestDTO.status());
        task.setPriority(taskRequestDTO.priority());
        task.setDueDate(taskRequestDTO.dueDate());

        return TaskMapper.entityToResponse(taskRepository.save(task));
    }

    @Override
    public String deleteTaskById(Long taskId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task is not found!"));

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(task.getProject().getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        boolean isOwnerOrAdmin = (membership.getRole() == Role.OWNER || membership.getRole() == Role.ADMIN);
        boolean isProjectManager = task.getProject().getManager().getId().equals(currentUser.getId());  // we dont want any manager can delete this task

        if (!isOwnerOrAdmin && !isProjectManager) {
            throw new RuntimeException("Access Denied: Only Project Managers or Admins can delete tasks.");
        }

//        IDOR (Insecure Direct Object Reference) taskRepository.deleteById(task);
        taskRepository.delete(task);

        return "deleted Successfully!";
    }

    @Override
    public TaskResponseDTO assignTaskToMember(Long taskId, Long memberId) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task is not found!"));

        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("member not found"));

        if(!companyMembershipRepository.existsByCompanyAndUser(task.getProject().getCompany(), member)){
            throw new RuntimeException("member id does not belonged to this company!");
        }

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(task.getProject().getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company! to assign task"));

        boolean isOwnerOrAdmin = (membership.getRole() == Role.OWNER || membership.getRole() == Role.ADMIN);
        boolean isProjectManager = task.getProject().getManager().getId().equals(currentUser.getId());

        if (!isOwnerOrAdmin && !isProjectManager) {
            throw new RuntimeException("Access Denied: Only Project Managers or Admins can assign tasks to a member.");
        }

        task.setAssignedTo(member);
        taskRepository.save(task);

        return TaskMapper.entityToResponse(task);
    }

}

