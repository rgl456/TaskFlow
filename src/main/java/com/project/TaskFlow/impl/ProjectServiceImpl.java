package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.AddMemberRequestDTO;
import com.project.TaskFlow.dto.ProjectRequestDTO;
import com.project.TaskFlow.dto.ProjectResponseDTO;
import com.project.TaskFlow.dto.ProjectUpdateRequestDTO;
import com.project.TaskFlow.mapper.ProjectMapper;
import com.project.TaskFlow.model.*;
import com.project.TaskFlow.repository.CompanyMembershipRepository;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.repository.ProjectRepository;
import com.project.TaskFlow.repository.UserRepository;
import com.project.TaskFlow.service.ProjectService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, CompanyRepository companyRepository, UserRepository userRepository, CompanyMembershipRepository companyMembershipRepository) {
        this.projectRepository = projectRepository;
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.companyMembershipRepository = companyMembershipRepository;
    }

    @Override
    public ProjectResponseDTO createProject(ProjectRequestDTO requestDTO) {
        Company company = companyRepository.findById(requestDTO.companyId())
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ requestDTO.companyId()));
        User manager = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ requestDTO.userId()));

        CompanyMembership companyMembership = companyMembershipRepository.findByCompanyAndUser(company, manager)
                .orElseThrow(() -> new RuntimeException("User not belonged in this company"));

        if(companyMembership.getRole().equals(Role.MEMBER)){
            throw new RuntimeException("members don't have access to create project");
        }

        Project project = new Project();
        project.setName(requestDTO.name());
        project.setDescription(requestDTO.description());
        project.setCompany(company);
        project.setManager(manager);
        Project savedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectResponseDTO(savedProject);
    }

    @Override
    public ProjectResponseDTO getProjectById(Long projectId) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id " + projectId));

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(project.getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        if (membership.getRole() == Role.MEMBER) {
            throw new RuntimeException("Access Denied: Only Managers or Owners can get project details.");
        }

        return ProjectMapper.mapToProjectResponseDTO(project);
    }

    @Override
    public ProjectResponseDTO addMemberToProject(Long projectId, AddMemberRequestDTO requestDTO) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("project not found with this id "+projectId));
        User member = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ requestDTO.userId()));

        if(!companyMembershipRepository.existsByCompanyAndUser(project.getCompany(), member)){
            throw new RuntimeException("User does not belong to this company! Cannot add external users.");
        }

        if (project.getMembers().contains(member)) {
            throw new RuntimeException("User is already a member of this project!");
        }

        project.getMembers().add(member);
        Project savedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectResponseDTO(savedProject);
    }

    @Override
    public ProjectResponseDTO updateProject(Long projectId, ProjectUpdateRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User currentUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found with id " + projectId));

        CompanyMembership membership = companyMembershipRepository
                .findByCompanyAndUser(project.getCompany(), currentUser)
                .orElseThrow(() -> new RuntimeException("You are not a member of this company!"));

        if (membership.getRole() == Role.MEMBER) {
            throw new RuntimeException("Access Denied: Only Managers or Owners can update project details.");
        }

        project.setName(requestDTO.name());
        project.setDescription(requestDTO.description());
        Project savedProject = projectRepository.save(project);

        return ProjectMapper.mapToProjectResponseDTO(savedProject);
    }


}
