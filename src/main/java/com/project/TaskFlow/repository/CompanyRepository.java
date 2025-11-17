package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByNameAndLocation(String name, String location);
}
