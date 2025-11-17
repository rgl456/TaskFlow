package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.mapper.CompanyMapper;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.service.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    @Transactional
    public CompanyResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {
        if(companyRepository.existsByNameAndLocation(companyRequestDTO.name(), companyRequestDTO.location())){
            throw new RuntimeException("Company with same name & location already exists!");
        }
        Company savedCompany = companyRepository.save(CompanyMapper.dtoToEntity(companyRequestDTO));
        return CompanyMapper.entityToResponse(savedCompany);
    }

    @Override
    public CompanyResponseDTO findCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ id));
        return CompanyMapper.entityToResponse(company);
    }

}
