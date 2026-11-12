package com.bank.loan.config;

import com.bank.loan.model.Admin;
import com.bank.loan.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (adminRepository.count() == 0) {
            Admin admin = new Admin();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ADMIN");
            adminRepository.save(admin);
            System.out.println("========================================");
            System.out.println("Default Admin Created Successfully!");
            System.out.println("Username: admin");
            System.out.println("Password: admin123");
            System.out.println("========================================");
        }
    }
}
