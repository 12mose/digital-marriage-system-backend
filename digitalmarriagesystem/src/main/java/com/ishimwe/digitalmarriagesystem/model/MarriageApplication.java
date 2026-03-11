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

    public MarriageApplication(){}

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public LocalDateTime getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDateTime submissionDate) {
        this.submissionDate = submissionDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public Long getApplicant1Id() {
        return applicant1Id;
    }

    public void setApplicant1Id(Long applicant1Id) {
        this.applicant1Id = applicant1Id;
    }

    public Long getApplicant2Id() {
        return applicant2Id;
    }

    public void setApplicant2Id(Long applicant2Id) {
        this.applicant2Id = applicant2Id;
    }
}