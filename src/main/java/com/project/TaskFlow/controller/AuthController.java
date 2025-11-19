package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.*;
import com.project.TaskFlow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse(
                        "Registered Successfully!",
                        authService.register(requestDTO),
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody AuthRequestDTO authRequestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Login SuccessFull!",
                        authService.authenticate(authRequestDTO),
                        LocalDateTime.now()
                ));
    }

    @PostMapping("/switch-company")
    public ResponseEntity<ApiResponse> switchCompany(@Valid @RequestBody CompanySwitchRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "Company Assigned SuccessFully!",
                        authService.switchCompany(requestDTO),
                        LocalDateTime.now()
                ));
    }

}
