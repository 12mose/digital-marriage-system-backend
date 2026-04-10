package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentType;
    private String filePath;
    private java.time.LocalDateTime uploadDate = java.time.LocalDateTime.now();
    private String verificationStatus = "Unverified";
    private Long applicationId;

    public Document(){}

    public Long getDocumentId() {
        return documentId;
    }

    public void setDocumentId(Long documentId) {
        this.documentId = documentId;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public java.time.LocalDateTime getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(java.time.LocalDateTime uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }
}