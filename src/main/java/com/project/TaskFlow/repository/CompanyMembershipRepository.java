package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {
    boolean existsByCompanyAndUser(Company company, User member);
}
