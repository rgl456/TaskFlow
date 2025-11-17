package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.model.Company;

import java.time.LocalDateTime;

public class CompanyMapper {

    public static Company dtoToEntity(CompanyRequestDTO companyRequestDTO){
        Company company = new Company();
        company.setName(companyRequestDTO.name());
        company.setOwnerEmail(companyRequestDTO.ownerEmail());
        company.setLocation(companyRequestDTO.location());
        return company;
    }

    public static CompanyResponseDTO entityToResponse(Company company){
        return new CompanyResponseDTO(
                company.getId(),
                company.getName(),
                company.getOwnerEmail(),
                company.getLocation()
        );
    }

}
