package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="marriages")
public class Marriage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long marriageId;

    private Long applicant1Id;
    private Long applicant2Id;
    private LocalDate marriageDate;
    private String marriagePlace;
    private String officiant;
    private String status = "Active";
    private Long applicationId;

    public Marriage(){}

    public Long getMarriageId() {
        return marriageId;
    }

    public void setMarriageId(Long marriageId) {
        this.marriageId = marriageId;
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

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    public String getMarriagePlace() {
        return marriagePlace;
    }

    public void setMarriagePlace(String marriagePlace) {
        this.marriagePlace = marriagePlace;
    }

    public String getOfficiant() {
        return officiant;
    }

    public void setOfficiant(String officiant) {
        this.officiant = officiant;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
}
