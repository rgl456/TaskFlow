package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.CompanyUserResponseDTO;
import com.project.TaskFlow.dto.UserResponseDTO;
import com.project.TaskFlow.dto.UserUpdateRequestDTO;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.repository.CompanyMembershipRepository;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.repository.UserRepository;
import com.project.TaskFlow.service.UserService;
import jakarta.transaction.Transactional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final CompanyMembershipRepository companyMembershipRepository;
    private final CompanyRepository companyRepository;

    public UserServiceImpl(UserRepository userRepository, CompanyMembershipRepository companyMembershipRepository, CompanyRepository companyRepository) {
        this.userRepository = userRepository;
        this.companyMembershipRepository = companyMembershipRepository;
        this.companyRepository = companyRepository;
    }

    @Override
    public UserResponseDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ userId));

        List<CompanyMembership> companyMembershipList = companyMembershipRepository.findAllByUser(user);

        return new UserResponseDTO(
                userId,
                user.getName(),
                user.getEmail(),
                companyMembershipList.stream().map(companyMembership ->
                        new CompanyUserResponseDTO(
                                companyMembership.getCompany().getId(),
                                companyMembership.getCompany().getName(),
                                companyMembership.getCompany().getLocation(),
                                companyMembership.getRole().name(),
                                companyMembership.getJoinedAt())).toList()
        );
    }

    @Override
    public UserResponseDTO getUserById() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email "+ email));

        List<CompanyMembership> companyMembershipList = companyMembershipRepository.findAllByUser(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                companyMembershipList.stream().map(companyMembership ->
                        new CompanyUserResponseDTO(
                                companyMembership.getCompany().getId(),
                                companyMembership.getCompany().getName(),
                                companyMembership.getCompany().getLocation(),
                                companyMembership.getRole().name(),
                                companyMembership.getJoinedAt())).toList()
        );
    }

    @Override
    @Transactional
    public UserResponseDTO updateUser(UserUpdateRequestDTO requestDTO) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email "+ email));

        user.setName(requestDTO.name());
        userRepository.save(user);

        List<CompanyMembership> companyMembershipList = companyMembershipRepository.findAllByUser(user);

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                companyMembershipList.stream().map(companyMembership ->
                        new CompanyUserResponseDTO(
                                companyMembership.getCompany().getId(),
                                companyMembership.getCompany().getName(),
                                companyMembership.getCompany().getLocation(),
                                companyMembership.getRole().name(),
                                companyMembership.getJoinedAt())).toList()
        );

    }

    @Override
    @Transactional
    public String deleteUserById(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ userId));

        boolean isOwnerOfAnyCompany = companyMembershipRepository.existsByUserAndRole(user, Role.OWNER);

        if (isOwnerOfAnyCompany) {
            throw new RuntimeException("CANNOT DELETE ACCOUNT: You are the Owner of one or more companies. Please transfer ownership or delete the company first.");
        }

        List<CompanyMembership> memberships = companyMembershipRepository.findAllByUser(user);
        companyMembershipRepository.deleteAll(memberships);

        // user -> delete in tasks and
        // projects

        userRepository.deleteById(userId);

        return "User Deleted Successfully!";
    }

}
