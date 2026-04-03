package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.*;
import com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository;
import com.ishimwe.digitalmarriagesystem.repository.UserRepository;
import com.ishimwe.digitalmarriagesystem.security.SecurityUtils;
import com.ishimwe.digitalmarriagesystem.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MarriageApplicationServiceImpl implements MarriageApplicationService {

    private final MarriageApplicationRepository marriageApplicationRepository;
    private final MarriageService marriageService;
    private final CertificateService certificateService;
    private final AuditLogService auditLogService;
    private final SecurityUtils securityUtils;
    private final UserRepository userRepository;
    private final com.ishimwe.digitalmarriagesystem.repository.MarriageRepository marriageRepository;

    public MarriageApplicationServiceImpl(MarriageApplicationRepository marriageApplicationRepository,
                                           MarriageService marriageService,
                                           CertificateService certificateService,
                                           AuditLogService auditLogService,
                                           SecurityUtils securityUtils,
                                           UserRepository userRepository,
                                           com.ishimwe.digitalmarriagesystem.repository.MarriageRepository marriageRepository) {
        this.marriageApplicationRepository = marriageApplicationRepository;
        this.marriageService = marriageService;
        this.certificateService = certificateService;
        this.auditLogService = auditLogService;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.marriageRepository = marriageRepository;
    }

    @Override
    public MarriageApplication saveApplication(MarriageApplication application) {
        // 1. Validation Logic: Distinct applicants
        if (application.getApplicant1Id() == null || application.getApplicant2Id() == null) {
            throw new RuntimeException("Both applicants must be provided");
        }
        if (application.getApplicant1Id().equals(application.getApplicant2Id())) {
            throw new RuntimeException("Applicant 1 and Applicant 2 must be different people");
        }

        // 2. Fetch User Profiles
        User applicant1 = userRepository.findById(application.getApplicant1Id())
                .orElseThrow(() -> new RuntimeException("Applicant 1 does not exist"));
        User applicant2 = userRepository.findById(application.getApplicant2Id())
                .orElseThrow(() -> new RuntimeException("Applicant 2 does not exist"));

        // 3. Advanced Rule: Age Validation (18+)
        if (applicant1.getBirthDate() != null) {
            if (applicant1.getBirthDate().plusYears(18).isAfter(LocalDate.now())) {
                throw new RuntimeException("Applicant 1 must be at least 18 years old");
            }
        }
        if (applicant2.getBirthDate() != null) {
            if (applicant2.getBirthDate().plusYears(18).isAfter(LocalDate.now())) {
                throw new RuntimeException("Applicant 2 must be at least 18 years old");
            }
        }

        // 4. Advanced Rule: Bigamy Prevention
        if (marriageRepository.isUserAlreadyMarried(application.getApplicant1Id())) {
            throw new RuntimeException("Applicant 1 is already in an ACTIVE marriage");
        }
        if (marriageRepository.isUserAlreadyMarried(application.getApplicant2Id())) {
            throw new RuntimeException("Applicant 2 is already in an ACTIVE marriage");
        }

        if (application.getSubmissionDate() == null) {
            application.setSubmissionDate(LocalDateTime.now());
        }
        if (application.getApplicationStatus() == null) {
            application.setApplicationStatus("PENDING");
        }
        return marriageApplicationRepository.save(application);
    }

    @Override
    public List<MarriageApplication> getAllApplications() {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            Optional<User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                Long userId = user.get().getUserId();
                return marriageApplicationRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
            }
        }
        return marriageApplicationRepository.findAll();
    }

    @Override
    public MarriageApplication getApplicationById(Long id) {
        Optional<MarriageApplication> applicationOpt = marriageApplicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            MarriageApplication application = applicationOpt.get();
            if (securityUtils.hasRole("CITIZEN")) {
                String email = securityUtils.getCurrentUserEmail();
                Optional<User> user = userRepository.findByEmail(email);
                if (user.isPresent()) {
                    Long userId = user.get().getUserId();
                    if (!userId.equals(application.getApplicant1Id()) && !userId.equals(application.getApplicant2Id())) {
                        throw new RuntimeException("Unauthorized access to this application");
                    }
                }
            }
            return application;
        }
        return null;
    }

    @Override
    public void deleteApplication(Long id) {
        marriageApplicationRepository.deleteById(id);
    }

    @Override
    @Transactional
    public MarriageApplication updateApplicationStatus(Long id, String status) {
        Optional<MarriageApplication> applicationOpt = marriageApplicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            MarriageApplication application = applicationOpt.get();
            String oldStatus = application.getApplicationStatus();
            application.setApplicationStatus(status);
            MarriageApplication updatedApplication = marriageApplicationRepository.save(application);

            // Workflow Logic: Automatically create Marriage and Certificate if APPROVED
            if ("APPROVED".equalsIgnoreCase(status) && !"APPROVED".equalsIgnoreCase(oldStatus)) {
                Marriage marriage = new Marriage();
                marriage.setApplicant1Id(application.getApplicant1Id());
                marriage.setApplicant2Id(application.getApplicant2Id());
                marriage.setMarriageDate(LocalDate.now());
                marriage.setMarriageStatus("ACTIVE");
                Marriage savedMarriage = marriageService.saveMarriage(marriage);

                Certificate certificate = new Certificate();
                certificate.setMarriageId(savedMarriage.getMarriageId());
                certificate.setIssueDate(LocalDate.now());
                certificateService.saveCertificate(certificate);
            }

            // Audit Log
            AuditLog log = new AuditLog();
            log.setAction("UPDATED APPLICATION STATUS FROM " + oldStatus + " TO " + status);
            log.setActionDate(LocalDateTime.now());
            log.setUserEmail(securityUtils.getCurrentUserEmail());
            auditLogService.saveAuditLog(log);

            return updatedApplication;
        }
        return null;
    }

    @Override
    public List<MarriageApplication> getApplicationsByStatus(String status) {
        return marriageApplicationRepository.findByApplicationStatus(status);
    }
}
