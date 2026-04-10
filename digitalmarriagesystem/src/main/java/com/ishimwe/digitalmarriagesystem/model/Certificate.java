package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name="certificate")
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificateId;

    private String certificateNumber;
    private LocalDate issueDate;
    private String verificationCode;
    private Long marriageId;
    private String qrCodeUrl;

    @Column(nullable = false)
    private boolean digitallySigned = false;

    private LocalDateTime signatureDate;

    public Certificate(){}

    public Long getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Long certificateId) {
        this.certificateId = certificateId;
    }

    public String getCertificateNumber() {
        return certificateNumber;
    }

    public void setCertificateNumber(String certificateNumber) {
        this.certificateNumber = certificateNumber;
    }

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public boolean isDigitallySigned() {
        return digitallySigned;
    }

    public void setDigitallySigned(boolean digitallySigned) {
        this.digitallySigned = digitallySigned;
    }

    public java.time.LocalDateTime getSignatureDate() {
        return signatureDate;
    }

    public void setSignatureDate(java.time.LocalDateTime signatureDate) {
        this.signatureDate = signatureDate;
    }

    public Long getMarriageId() {
        return marriageId;
    }

    public void setMarriageId(Long marriageId) {
        this.marriageId = marriageId;
    }
}