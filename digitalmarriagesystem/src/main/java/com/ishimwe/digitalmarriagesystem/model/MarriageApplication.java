package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="marriage_application")
public class MarriageApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    private LocalDateTime submissionDate;
    private String applicationStatus;

    private Long applicant1Id;
    private Long applicant2Id;
    private LocalDateTime reviewDate;
    private String rejectionReason;
    private Long reviewedBy;

    // New Fields: Witnesses
    private String witness1Name;
    private String witness1NationalId;
    private String witness2Name;
    private String witness2NationalId;

    // New Fields: Partner Consent
    private boolean partner2Approved = false;

    public MarriageApplication(){}

    public Long getApplicationId() { return applicationId; }
    public void setApplicationId(Long applicationId) { this.applicationId = applicationId; }

    public LocalDateTime getSubmissionDate() { return submissionDate; }
    public void setSubmissionDate(LocalDateTime submissionDate) { this.submissionDate = submissionDate; }

    public String getApplicationStatus() { return applicationStatus; }
    public void setApplicationStatus(String applicationStatus) { this.applicationStatus = applicationStatus; }

    public Long getApplicant1Id() { return applicant1Id; }
    public void setApplicant1Id(Long applicant1Id) { this.applicant1Id = applicant1Id; }

    public Long getApplicant2Id() { return applicant2Id; }
    public void setApplicant2Id(Long applicant2Id) { this.applicant2Id = applicant2Id; }

    public LocalDateTime getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDateTime reviewDate) { this.reviewDate = reviewDate; }

    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String rejectionReason) { this.rejectionReason = rejectionReason; }

    public Long getReviewedBy() { return reviewedBy; }
    public void setReviewedBy(Long reviewedBy) { this.reviewedBy = reviewedBy; }

    public String getWitness1Name() { return witness1Name; }
    public void setWitness1Name(String witness1Name) { this.witness1Name = witness1Name; }

    public String getWitness1NationalId() { return witness1NationalId; }
    public void setWitness1NationalId(String witness1NationalId) { this.witness1NationalId = witness1NationalId; }

    public String getWitness2Name() { return witness2Name; }
    public void setWitness2Name(String witness2Name) { this.witness2Name = witness2Name; }

    public String getWitness2NationalId() { return witness2NationalId; }
    public void setWitness2NationalId(String witness2NationalId) { this.witness2NationalId = witness2NationalId; }

    public boolean isPartner2Approved() { return partner2Approved; }
    public void setPartner2Approved(boolean partner2Approved) { this.partner2Approved = partner2Approved; }
}