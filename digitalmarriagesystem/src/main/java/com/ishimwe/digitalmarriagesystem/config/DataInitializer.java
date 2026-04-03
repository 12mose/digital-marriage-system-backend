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
            admin.setRole("ADMIN");
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
            officer.setRole("OFFICER");
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
            citizen.setRole("CITIZEN");
            citizen.setNationalId("30000000000");
            citizen.setVerified(true);
            userRepository.save(citizen);
            System.out.println(">>> Created Citizen account: citizen@dms.com / citizen123");
        }
    }
}
