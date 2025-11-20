package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyOwnerResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;

public interface CompanyService {
    CompanyOwnerResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);
    CompanyOwnerResponseDTO findCompanyById(Long id);
    CompanyOwnerResponseDTO updateOwnerEmailId(Long id, String email);
    CompanyMembershipResponseDTO addUserToCompanyById(Long id, MemberRequestDTO requestDTO);
    CompanyMembershipResponseDTO updateRoleFromUserToMember(Long id, Long memberId);
    CompanyMembershipResponseDTO updateRoleFromUserToManager(Long id, Long memberId);
}
