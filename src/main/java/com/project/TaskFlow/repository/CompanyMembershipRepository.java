package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
import com.project.TaskFlow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {
    boolean existsByCompanyAndUser(Company company, User member);
    Optional<CompanyMembership> findByCompany(Company company);
    Optional<CompanyMembership> findByCompanyAndRole(Company company, Role role);
    Optional<CompanyMembership> findByCompanyAndUser(Company company, User newOwner);
    List<CompanyMembership> findAllByUser(User user);
}
