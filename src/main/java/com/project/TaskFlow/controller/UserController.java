package com.project.TaskFlow.controller;

import com.project.TaskFlow.dto.ApiResponse;
import com.project.TaskFlow.dto.UserResponseDTO;
import com.project.TaskFlow.dto.UserUpdateRequestDTO;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "user details fetched successfully!",
                        userService.getUserById(userId),
                        LocalDateTime.now()
        ));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse> getUserById(){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse(
                        "user details fetched successfully!",
                        userService.getUserById(),
                        LocalDateTime.now()
                ));
    }

    @PutMapping("/profile")
    public ResponseEntity<ApiResponse> updateUserById(@Valid @RequestBody UserUpdateRequestDTO requestDTO){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                        "User profile updated Successfully!",
                        userService.updateUser(requestDTO),
                        LocalDateTime.now()
                ));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ApiResponse> deleteUserById(@PathVariable Long userId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse(
                   "user deleted Successfully!",
                        userService.deleteUserById(userId),
                        LocalDateTime.now()
                ));
    }

}
