package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.MarriageApplication;
import java.util.List;

public interface MarriageApplicationService {

    MarriageApplication saveApplication(MarriageApplication application);

    List<MarriageApplication> getAllApplications();

    MarriageApplication getApplicationById(Long id);

    void deleteApplication(Long id);

    MarriageApplication updateApplicationStatus(Long id, String status);

    List<MarriageApplication> getApplicationsByStatus(String status);

    MarriageApplication approveByPartner(Long id);
}
