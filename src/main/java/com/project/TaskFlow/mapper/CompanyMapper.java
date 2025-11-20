package com.project.TaskFlow.mapper;

import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyOwnerResponseDTO;
import com.project.TaskFlow.model.Company;

public class CompanyMapper {

    public static Company dtoToEntity(CompanyRequestDTO companyRequestDTO){
        Company company = new Company();
        company.setName(companyRequestDTO.name());
        company.setOwnerEmail(companyRequestDTO.ownerEmail());
        company.setLocation(companyRequestDTO.location());
        return company;
    }

    public static CompanyOwnerResponseDTO entityToResponse(Company company){
        return new CompanyOwnerResponseDTO(
                company.getId(),
                company.getName(),
                company.getOwnerEmail(),
                company.getLocation()
        );
    }

}
