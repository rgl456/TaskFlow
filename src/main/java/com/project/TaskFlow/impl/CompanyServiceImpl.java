package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.CompanyMembershipResponseDTO;
import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyOwnerResponseDTO;
import com.project.TaskFlow.dto.MemberRequestDTO;
import com.project.TaskFlow.mapper.CompanyMapper;
import com.project.TaskFlow.mapper.CompanyMembershipMapper;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
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
    public CompanyOwnerResponseDTO createCompany(CompanyRequestDTO companyRequestDTO) {
        User owner = userRepository.findByEmail(companyRequestDTO.ownerEmail())
                .orElseThrow(() -> new RuntimeException("Owner user not found"));

        if(companyRepository.existsByNameAndLocation(companyRequestDTO.name(), companyRequestDTO.location())){
            throw new RuntimeException("Company with same name & location already exists!");
        }
        Company savedCompany = companyRepository.save(CompanyMapper.dtoToEntity(companyRequestDTO));

        CompanyMembership membership = CompanyMembershipMapper.dtoToEntity(savedCompany, owner, Role.OWNER);

        companyMembershipRepository.save(membership);

        return CompanyMapper.entityToResponse(savedCompany);
    }

    @Override
    public CompanyOwnerResponseDTO findCompanyById(Long id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ id));
        return CompanyMapper.entityToResponse(company);
    }

    @Override
    @Transactional
    public CompanyOwnerResponseDTO updateOwnerEmailId(Long id, String email) {
        User newOwner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Owner user not found"));

        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ id));

        CompanyMembership membership = companyMembershipRepository.findByCompanyAndRole(company, Role.OWNER)
                .orElseThrow(() -> new RuntimeException("there is no owner assigned with this company"));

        membership.setRole(Role.MEMBER);

        companyMembershipRepository.save(membership);

        boolean isNewOwnerBelongsToSameCompany = companyMembershipRepository.existsByCompanyAndUser(company, newOwner);

        CompanyMembership newMemberShip;
        if(!isNewOwnerBelongsToSameCompany){
            newMemberShip = new CompanyMembership();
            newMemberShip.setCompany(company);
            newMemberShip.setUser(newOwner);
            newMemberShip.setRole(Role.OWNER);
        }
        else{
            newMemberShip = companyMembershipRepository.findByCompanyAndUser(company, newOwner)
                    .orElseThrow(() -> new RuntimeException("this owner not belonged to this company"));
            newMemberShip.setRole(Role.OWNER);
        }

        companyMembershipRepository.save(newMemberShip);

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

        CompanyMembership savedMembership = companyMembershipRepository
                .save(CompanyMembershipMapper.dtoToEntity(company, member, requestDTO.role()));

        return CompanyMembershipMapper.entityToResponse(savedMembership);
    }

    @Override
    @Transactional
    public CompanyMembershipResponseDTO updateRoleFromUserToMember(Long companyId, Long memberId) {
        return updateRoleToUser(companyId, memberId, Role.MEMBER);
    }

    @Override
    @Transactional
    public CompanyMembershipResponseDTO updateRoleFromUserToManager(Long companyId, Long memberId) {
        return updateRoleToUser(companyId, memberId, Role.MANAGER);
    }

    public CompanyMembershipResponseDTO updateRoleToUser(Long companyId, Long memberId, Role role) {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ companyId));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("User not found with this id "+ memberId));

        CompanyMembership membership = companyMembershipRepository.findByCompanyAndUser(company, member)
                .orElseThrow(() -> new RuntimeException("User not belonged in this company"));

        membership.setRole(role);

        CompanyMembership savedMembership = companyMembershipRepository.save(membership);

        return CompanyMembershipMapper.entityToResponse(savedMembership);
    }

}

