package com.project.TaskFlow.service;

import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.model.Company;

public interface CompanyService {
    CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO);
    CompanyResponseDTO findCompanyById(Long id);
}
