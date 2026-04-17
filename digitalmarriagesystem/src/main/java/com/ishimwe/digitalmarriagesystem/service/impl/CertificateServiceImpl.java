package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Certificate;
import com.ishimwe.digitalmarriagesystem.repository.CertificateRepository;
import com.ishimwe.digitalmarriagesystem.service.CertificateService;
import com.ishimwe.digitalmarriagesystem.exception.ApiException;
import com.ishimwe.digitalmarriagesystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;
    private final com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils;
    private final com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository;
    private final com.ishimwe.digitalmarriagesystem.repository.MarriageRepository marriageRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository,
                                  com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils,
                                  com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository,
                                  com.ishimwe.digitalmarriagesystem.repository.MarriageRepository marriageRepository) {
        this.certificateRepository = certificateRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.marriageRepository = marriageRepository;
    }

    @Override
    public Certificate saveCertificate(Certificate certificate) {
        if (certificate.getIssueDate() == null) {
            certificate.setIssueDate(LocalDate.now());
        }
        if (certificate.getCertificateNumber() == null) {
            certificate.setCertificateNumber(generateCertificateNumber());
        }
        if (certificate.getVerificationCode() == null) {
            certificate.setVerificationCode(java.util.UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        }
        
        // Populate new fields
        certificate.setQrCodeUrl("http://localhost:8081/verify.html?number=" + certificate.getCertificateNumber() + "&code=" + certificate.getVerificationCode());
        certificate.setDigitallySigned(true);
        certificate.setSignatureDate(java.time.LocalDateTime.now());
        
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().getUserId();
                List<com.ishimwe.digitalmarriagesystem.model.Marriage> marriages = marriageRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
                List<Long> marriageIds = marriages.stream().map(com.ishimwe.digitalmarriagesystem.model.Marriage::getMarriageId).collect(java.util.stream.Collectors.toList());
                if (marriageIds.isEmpty()) return java.util.Collections.emptyList();
                return certificateRepository.findByMarriageIdIn(marriageIds);
            }
        }
        return certificateRepository.findAll();
    }

    @Override
    public Certificate getCertificateById(Long id) {
        Optional<Certificate> certificateOpt = certificateRepository.findById(id);
        if (certificateOpt.isPresent()) {
            Certificate certificate = certificateOpt.get();
            if (securityUtils.hasRole("CITIZEN")) {
                String email = securityUtils.getCurrentUserEmail();
                java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    Long userId = userOpt.get().getUserId();
                    com.ishimwe.digitalmarriagesystem.model.Marriage marriage = marriageRepository.findById(certificate.getMarriageId()).orElse(null);
                    if (marriage == null || (!userId.equals(marriage.getApplicant1Id()) && !userId.equals(marriage.getApplicant2Id()))) {
                        throw new ApiException("Unauthorized access to this certificate");
                    }
                }
            }
            return certificate;
        }
        throw new ResourceNotFoundException("Certificate not found with id: " + id);
    }

    @Override
    public void deleteCertificate(Long id) {
        certificateRepository.deleteById(id);
    }

    @Override
    public Certificate updateCertificateNumber(Long id, String certificateNumber) {
        Optional<Certificate> certificateOpt = certificateRepository.findById(id);
        if (certificateOpt.isPresent()) {
            Certificate certificate = certificateOpt.get();
            certificate.setCertificateNumber(certificateNumber);
            return certificateRepository.save(certificate);
        }
        throw new ResourceNotFoundException("Certificate not found with id: " + id);
    }

    @Override
    public Certificate verifyCertificate(String certificateNumber, String verificationCode) {
        return certificateRepository.findByCertificateNumberAndVerificationCode(certificateNumber, verificationCode)
                .orElseThrow(() -> new ResourceNotFoundException("No valid certificate found for provided verification details."));
    }

    private String generateCertificateNumber() {
        return "CERT-" + System.currentTimeMillis();
    }
}
