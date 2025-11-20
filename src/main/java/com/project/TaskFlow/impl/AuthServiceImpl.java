package com.project.TaskFlow.impl;

import com.project.TaskFlow.dto.*;
import com.project.TaskFlow.jwt.JwtService;
import com.project.TaskFlow.mapper.UserMapper;
import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.repository.CompanyMembershipRepository;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.repository.UserRepository;
import com.project.TaskFlow.security.CustomUserDetails;
import com.project.TaskFlow.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthServiceImpl implements AuthService{

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    private final CompanyRepository companyRepository;
    private final CompanyMembershipRepository companyMembershipRepository;

    public AuthServiceImpl(PasswordEncoder passwordEncoder, UserRepository userRepository, AuthenticationManager authenticationManager, JwtService jwtService, UserDetailsService userDetailsService, CompanyRepository companyRepository, CompanyMembershipRepository companyMembershipRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userDetailsService = userDetailsService;
        this.companyRepository = companyRepository;
        this.companyMembershipRepository = companyMembershipRepository;
    }

    @Override
    public AuthResponseDTO register(UserRequestDTO requestDTO) {
        User user = UserMapper.dtoToEntity(requestDTO);  // name email password
        user.setPassword(passwordEncoder.encode(requestDTO.password()));
        User savedUser = userRepository.save(user);

        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);
        String expireAt = jwtService.extractExpiration(accessToken).toString();

        return new AuthResponseDTO(
                savedUser.getEmail(),
                "",
                accessToken,
                refreshToken,
                expireAt
        );
    }

    @Override
    public AuthResponseDTO authenticate(AuthRequestDTO authRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequestDTO.email(), authRequestDTO.password())
        );
        if(!authentication.isAuthenticated()){
            throw new RuntimeException("invalid credentials try again!");
        }
        User user = userRepository.findByEmail(authRequestDTO.email())
                .orElseThrow(() -> new RuntimeException("User not belonged in this company"));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        String expireAt = jwtService.extractExpiration(accessToken).toString();

        return new AuthResponseDTO(
                authRequestDTO.email(),
                "",
                accessToken,
                refreshToken,
                expireAt
        );
    }

    @Override
    public CompanyTokenResponseDTO switchCompany(CompanySwitchRequestDTO requestDTO) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found!"));
        Company company = companyRepository.findById(requestDTO.companyId())
                .orElseThrow(() -> new RuntimeException("Company not found with this id "+ requestDTO.companyId()));
        CompanyMembership membership = companyMembershipRepository.findByCompanyAndUser(company, user)
                .orElseThrow(() -> new RuntimeException("User not belonged in this company"));

        String companyAccessToken = jwtService.generateCompanyAccessToken(
                user,
                membership.getCompany().getId(),
                membership.getRole()
        );

        String expireAt = jwtService.extractExpiration(companyAccessToken).toString();

        return new CompanyTokenResponseDTO(
                membership.getCompany().getId(),
                membership.getUser().getId(),
                membership.getRole().name(),
                companyAccessToken,
                expireAt
        );

    }

    @Override
    public Object refresh(RefreshTokenRequestDTO requestDTO) {
        String refreshToken = requestDTO.refreshToken();
        String email = jwtService.extractUserEmail(refreshToken);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("user not found!"));

        UserDetails userDetails = new CustomUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new RuntimeException("Invalid Refresh Token");
        }

        String newAccessToken = "";

        if(requestDTO.companyId() != null){
            Company company = companyRepository.findById(requestDTO.companyId())
                    .orElseThrow(() -> new RuntimeException("Company not found with this id "+ requestDTO.companyId()));
            CompanyMembership companyMembership = companyMembershipRepository.findByCompanyAndUser(company, user)
                    .orElseThrow(() -> new RuntimeException("User not belonged in this company"));
            newAccessToken = jwtService.generateCompanyAccessToken(user, requestDTO.companyId(), companyMembership.getRole());

            String expireAt = jwtService.extractExpiration(newAccessToken).toString();

            return new CompanyTokenResponseDTO(
                    companyMembership.getCompany().getId(),
                    companyMembership.getUser().getId(),
                    companyMembership.getRole().name(),
                    newAccessToken,
                    expireAt
            );
        }

        newAccessToken = jwtService.generateAccessToken(user);
        String expireAt = jwtService.extractExpiration(newAccessToken).toString();
        return new AuthResponseDTO(
                email,
                "",
                newAccessToken,
                refreshToken,
                expireAt
        );

    }

}
