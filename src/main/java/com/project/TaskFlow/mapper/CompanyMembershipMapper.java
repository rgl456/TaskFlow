package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.User;

public class CompanyMembershipMapper {

    public static CompanyMembership dtoToEntity(Company company, User member, MemberRequestDTO requestDTO){
        CompanyMembership membership = new CompanyMembership();
        membership.setCompany(company);
        membership.setUser(member);
        membership.setRole(requestDTO.role());
        return membership;
    }

    public static CompanyMembershipResponseDTO entityToResponse(CompanyMembership membership){
        return new CompanyMembershipResponseDTO(
                membership.getId(),
                membership.getCompany().getId(),
                membership.getUser().getId(),
                membership.getRole()
        );
    }
}
