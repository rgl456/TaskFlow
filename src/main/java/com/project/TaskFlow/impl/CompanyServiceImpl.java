package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;
import com.project.TaskFlow.mapper.CompanyMapper;
import com.project.TaskFlow.mapper.CompanyMembershipMapper;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.repository.CompanyMembershipRepository;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.repository.UserRepository;
import com.project.TaskFlow.service.CompanyService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final UserRepository userRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository, UserRepository userRepository, CompanyMembershipRepository companyMembershipRepository) {
        this.companyRepository = companyRepository;
        this.userRepository = userRepository;
        this.companyMembershipRepository = companyMembershipRepository;
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

    @Override
    @Transactional
    public CompanyResponseDTO updateOwnerEmailId(Long id, String email) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ id));
        company.setOwnerEmail(email);
        Company savedCompany = companyRepository.save(company);
        return CompanyMapper.entityToResponse(savedCompany);
    }

    @Override
    @Transactional
    public CompanyMembershipResponseDTO addUserToCompanyById(Long id, MemberRequestDTO requestDTO) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ id));

        User member = userRepository.findById(requestDTO.userId())
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ id));

        if(companyMembershipRepository.existsByCompanyAndUser(company, member)){
            throw new RuntimeException("User already in this company");
        }

        CompanyMembership savedMembership =
                companyMembershipRepository.save(CompanyMembershipMapper.dtoToEntity(company, member, requestDTO));

        return CompanyMembershipMapper.entityToResponse(savedMembership);
    }

}
