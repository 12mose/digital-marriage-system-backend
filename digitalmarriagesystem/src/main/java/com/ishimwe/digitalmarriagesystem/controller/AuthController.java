package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.dto.AuthResponse;
import com.ishimwe.digitalmarriagesystem.dto.LoginRequest;
import com.ishimwe.digitalmarriagesystem.model.User;
import com.ishimwe.digitalmarriagesystem.repository.UserRepository;
import com.ishimwe.digitalmarriagesystem.security.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final com.ishimwe.digitalmarriagesystem.service.EmailService emailService;

    public AuthController(AuthenticationManager authenticationManager, JwtUtils jwtUtils, 
                          UserRepository userRepository, PasswordEncoder passwordEncoder,
                          com.ishimwe.digitalmarriagesystem.service.EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtils.generateToken(userDetails);
            
            User user = userRepository.findByEmail(loginRequest.getEmail()).get();

            return ResponseEntity.ok(new AuthResponse(jwt, user.getEmail(), user.getRole(), user.getUserId()));
        } catch (org.springframework.security.authentication.DisabledException e) {
            return ResponseEntity.status(403).body("Account not verified. Please check your email.");
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered.");
        }
        
        if (user.getRole() == null || user.getRole().isBlank()) {
            user.setRole("Citizen");
        }
        // Ensure role matches one of the expected document roles
        String currentRole = user.getRole();
        if (currentRole.equalsIgnoreCase("Admin")) user.setRole("Admin");
        else if (currentRole.equalsIgnoreCase("Citizen")) user.setRole("Citizen");
        else if (currentRole.equalsIgnoreCase("Marriage Officer") || currentRole.equalsIgnoreCase("Officer")) user.setRole("Marriage Officer");
        
        String token = java.util.UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setVerified(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        User savedUser = userRepository.save(user);
        System.out.println(">>> User registered successfully in database: " + savedUser.getEmail());
        
        try {
            String verificationUrl = "http://localhost:8081/api/auth/verify?token=" + token;
            System.out.println(">>> Verification Link: " + verificationUrl);
            
            emailService.sendVerificationEmail(savedUser.getEmail(), token);
        } catch (Exception e) {
            System.err.println("Error sending email: " + e.getMessage());
            // In production, you might want to handle this differently
        }

        return ResponseEntity.status(201).body("Registration successful. You can now login with your credentials.");
    }

    @org.springframework.web.bind.annotation.GetMapping("/verify")
    public ResponseEntity<?> verify(@org.springframework.web.bind.annotation.RequestParam String token) {
        java.util.Optional<User> userOpt = userRepository.findAll().stream()
                .filter(u -> token.equals(u.getVerificationToken()))
                .findFirst();

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setVerified(true);
            user.setVerificationToken(null);
            userRepository.save(user);
            return ResponseEntity.ok("Account verified successfully. You can now login.");
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired verification token.");
        }
    }
}
