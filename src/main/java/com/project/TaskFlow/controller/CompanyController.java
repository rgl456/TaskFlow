package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.ApiResponse;
import com.project.TaskFlow.dto.CompanyRequestDTO;
import com.project.TaskFlow.dto.CompanyResponseDTO;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCompany(@Valid @RequestBody CompanyRequestDTO companyRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        "Company created Successfully",
                        companyService.createCompany(companyRequestDTO),
                        LocalDateTime.now()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponseDTO> findCompanyById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(companyService.findCompanyById(id));
    }



}
