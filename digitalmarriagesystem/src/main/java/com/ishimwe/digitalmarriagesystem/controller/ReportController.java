package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.repository.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    private final UserRepository userRepository;
    private final MarriageApplicationRepository marriageApplicationRepository;
    private final MarriageRepository marriageRepository;
    private final CertificateRepository certificateRepository;

    public ReportController(UserRepository userRepository,
                            MarriageApplicationRepository marriageApplicationRepository,
                            MarriageRepository marriageRepository,
                            CertificateRepository certificateRepository) {
        this.userRepository = userRepository;
        this.marriageApplicationRepository = marriageApplicationRepository;
        this.marriageRepository = marriageRepository;
        this.certificateRepository = certificateRepository;
    }

    @GetMapping("/stats")
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userRepository.count());
        stats.put("totalApplications", marriageApplicationRepository.count());
        stats.put("totalMarriages", marriageRepository.count());
        stats.put("totalCertificates", certificateRepository.count());
        
        // Detailed break down
        stats.put("pendingApplications", marriageApplicationRepository.findByApplicationStatus("Pending").size());
        stats.put("activeMarriages", marriageRepository.findByStatus("Active").size());
        
        return stats;
    }
}
