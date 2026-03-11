package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.Certificate;
import com.ishimwe.digitalmarriagesystem.service.CertificateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @PostMapping
    public Certificate createCertificate(@RequestBody Certificate certificate) {
        return certificateService.saveCertificate(certificate);
    }

    @GetMapping
    public List<Certificate> getAllCertificates() {
        return certificateService.getAllCertificates();
    }

    @GetMapping("/{id}")
    public Certificate getCertificate(@PathVariable Long id) {
        return certificateService.getCertificateById(id);
    }

    @PutMapping("/{id}/number")
    public Certificate updateCertificateNumber(@PathVariable Long id, @RequestBody String certificateNumber) {
        return certificateService.updateCertificateNumber(id, certificateNumber);
    }

    @DeleteMapping("/{id}")
    public void deleteCertificate(@PathVariable Long id) {
        certificateService.deleteCertificate(id);
    }
}
