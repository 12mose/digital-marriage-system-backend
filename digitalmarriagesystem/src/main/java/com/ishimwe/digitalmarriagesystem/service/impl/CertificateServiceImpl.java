package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Certificate;
import com.ishimwe.digitalmarriagesystem.repository.CertificateRepository;
import com.ishimwe.digitalmarriagesystem.service.CertificateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CertificateServiceImpl implements CertificateService {

    private final CertificateRepository certificateRepository;

    public CertificateServiceImpl(CertificateRepository certificateRepository) {
        this.certificateRepository = certificateRepository;
    }

    @Override
    public Certificate saveCertificate(Certificate certificate) {
        if (certificate.getIssueDate() == null) {
            certificate.setIssueDate(LocalDate.now());
        }
        if (certificate.getCertificateNumber() == null) {
            certificate.setCertificateNumber(generateCertificateNumber());
        }
        return certificateRepository.save(certificate);
    }

    @Override
    public List<Certificate> getAllCertificates() {
        return certificateRepository.findAll();
    }

    @Override
    public Certificate getCertificateById(Long id) {
        Optional<Certificate> certificate = certificateRepository.findById(id);
        return certificate.orElse(null);
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
        return null;
    }

    private String generateCertificateNumber() {
        return "CERT-" + System.currentTimeMillis();
    }
}
