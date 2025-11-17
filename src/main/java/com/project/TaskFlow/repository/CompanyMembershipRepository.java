package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.CompanyMembership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyMembershipRepository extends JpaRepository<CompanyMembership, Long> {
}
