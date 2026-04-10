package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.Certificate;
import java.util.List;

public interface CertificateService {

    Certificate saveCertificate(Certificate certificate);

    List<Certificate> getAllCertificates();

    Certificate getCertificateById(Long id);

    void deleteCertificate(Long id);

    Certificate updateCertificateNumber(Long id, String certificateNumber);

    Certificate verifyCertificate(String certificateNumber, String verificationCode);
}
