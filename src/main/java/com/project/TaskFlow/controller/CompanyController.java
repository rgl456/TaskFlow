package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.*;
import com.project.TaskFlow.service.CompanyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
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

    @PutMapping("/{id}/owner")
    public ResponseEntity<ApiResponse> updateOwnerEmailId(@PathVariable Long id, @Email @RequestParam("email") String email){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "owner email updated successfully!",
                        companyService.updateOwnerEmailId(id, email),
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/{id}/members")
    public ResponseEntity<ApiResponse> addMemberToCompanyById(@PathVariable Long id, @Valid @RequestBody MemberRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "user added to company successfully!",
                        companyService.addUserToCompanyById(id, requestDTO),
                        LocalDateTime.now())
                );
    }

}

