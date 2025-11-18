package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
import com.project.TaskFlow.model.User;

public class CompanyMembershipMapper {

    public static CompanyMembership dtoToEntity(Company company, User member, Role role){
        CompanyMembership membership = new CompanyMembership();
        membership.setCompany(company);
        membership.setUser(member);
        membership.setRole(role);
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
