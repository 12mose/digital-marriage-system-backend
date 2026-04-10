package com.ishimwe.digitalmarriagesystem.config;

import com.ishimwe.digitalmarriagesystem.model.User;
import com.ishimwe.digitalmarriagesystem.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataInitializer(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.findByEmail("admin@dms.com").isEmpty()) {
            User admin = new User();
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setEmail("admin@dms.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("Admin");
            admin.setNationalId("10000000000");
            admin.setVerified(true);
            userRepository.save(admin);
            System.out.println(">>> Created Admin account: admin@dms.com / admin123");
        }

        if (userRepository.findByEmail("officer@dms.com").isEmpty()) {
            User officer = new User();
            officer.setFirstName("Marriage");
            officer.setLastName("Officer");
            officer.setEmail("officer@dms.com");
            officer.setPassword(passwordEncoder.encode("officer123"));
            officer.setRole("Marriage Officer");
            officer.setNationalId("20000000000");
            officer.setVerified(true);
            userRepository.save(officer);
            System.out.println(">>> Created Officer account: officer@dms.com / officer123");
        }

        if (userRepository.findByEmail("citizen@dms.com").isEmpty()) {
            User citizen = new User();
            citizen.setFirstName("John");
            citizen.setLastName("Citizen");
            citizen.setEmail("citizen@dms.com");
            citizen.setPassword(passwordEncoder.encode("citizen123"));
            citizen.setRole("Citizen");
            citizen.setNationalId("30000000000");
            citizen.setVerified(true);
            userRepository.save(citizen);
        }
        
        // Automatically verify all existing users to ensure they can login
        userRepository.findAll().stream()
            .filter(u -> !u.isVerified())
            .forEach(u -> {
                u.setVerified(true);
                userRepository.save(u);
                System.out.println(">>> Verified existing user: " + u.getEmail());
            });
    }
}
