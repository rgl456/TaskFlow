package com.project.TaskFlow.seeder;

import com.project.TaskFlow.model.Company;
import com.project.TaskFlow.model.CompanyMembership;
import com.project.TaskFlow.model.Role;
import com.project.TaskFlow.model.User;
import com.project.TaskFlow.repository.CompanyMembershipRepository;
import com.project.TaskFlow.repository.CompanyRepository;
import com.project.TaskFlow.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
public class AdminDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMembershipRepository membershipRepository;
    private final PasswordEncoder passwordEncoder;

    // Read values from application.properties
    @Value("${app.admin.email}")
    private String adminEmail;

    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.admin.name}")
    private String adminName;

    public AdminDataSeeder(UserRepository userRepository,
                           CompanyRepository companyRepository,
                           CompanyMembershipRepository membershipRepository,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.membershipRepository = membershipRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // 1. Check if Admin already exists
        if (userRepository.findByEmail(adminEmail).isPresent()) {
            System.out.println("✅ Super Admin already exists. Skipping seeding.");
            return;
        }

        System.out.println("🚀 Creating Super Admin & System Company...");

        // 2. Create the "System" Company (Tenant for Admins)
        Company systemCompany = new Company();
        systemCompany.setName("TaskFlow HQ (System)");
        systemCompany.setLocation("Cloud");
        systemCompany.setOwnerEmail(adminEmail);
        // ... set other fields/timestamps
        Company savedCompany = companyRepository.save(systemCompany);

        // 3. Create the Super Admin User
        User adminUser = new User();
        adminUser.setName(adminName);
        adminUser.setEmail(adminEmail);
        adminUser.setPassword(passwordEncoder.encode(adminPassword)); // Hash it!
        // ... set timestamps
        User savedAdmin = userRepository.save(adminUser);

        // 4. Link them (Give him the Boss Role)
        CompanyMembership membership = new CompanyMembership();
        membership.setUser(savedAdmin);
        membership.setCompany(savedCompany);
        membership.setRole(Role.ADMIN); // Or create a special ROLE_SUPER_ADMIN enum
        membership.setJoinedAt(LocalDateTime.now());

        membershipRepository.save(membership);

        System.out.println("✅ Super Admin Created Successfully!");
        System.out.println("📧 Email: " + adminEmail);
        System.out.println("🔑 Password: " + adminPassword);
    }
}