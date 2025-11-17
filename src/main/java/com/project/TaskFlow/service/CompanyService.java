package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;
import com.project.TaskFlow.mapper.CompanyMembershipMapper;
import jakarta.validation.Valid;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);
    CompanyResponseDTO findCompanyById(Long id);
    CompanyResponseDTO updateOwnerEmailId(Long id, String email);
    CompanyMembershipResponseDTO addUserToCompanyById(Long id, MemberRequestDTO requestDTO);
}
