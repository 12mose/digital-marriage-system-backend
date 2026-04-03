package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.MarriageApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarriageApplicationRepository extends JpaRepository<MarriageApplication, Long> {
    List<MarriageApplication> findByApplicationStatus(String status);
    List<MarriageApplication> findByApplicant1IdOrApplicant2Id(Long applicant1Id, Long applicant2Id);
}
