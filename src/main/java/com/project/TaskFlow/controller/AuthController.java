package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.AuthResponseDTO;
import com.project.TaskFlow.dto.UserRequestDTO;
import com.project.TaskFlow.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(authService.register(requestDTO));
    }
}
