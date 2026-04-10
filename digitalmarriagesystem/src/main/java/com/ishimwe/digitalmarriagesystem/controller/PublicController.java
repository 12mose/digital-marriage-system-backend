package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.Certificate;
import com.ishimwe.digitalmarriagesystem.service.CertificateService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    private final CertificateService certificateService;

    public PublicController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping("/verify-certificate")
    public Certificate verifyCertificate(@RequestParam String number, @RequestParam String code) {
        return certificateService.verifyCertificate(number, code);
    }
}
