package com.project.TaskFlow.repository;

import com.project.TaskFlow.model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    boolean existsByNameAndLocation(String name, String location);
    List<Company> findALlByOwnerEmail(String oldEmail);
}
