package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.MarriageApplication;
import com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository;
import com.ishimwe.digitalmarriagesystem.service.MarriageApplicationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class MarriageApplicationServiceImpl implements MarriageApplicationService {

    private final MarriageApplicationRepository marriageApplicationRepository;

    public MarriageApplicationServiceImpl(MarriageApplicationRepository marriageApplicationRepository) {
        this.marriageApplicationRepository = marriageApplicationRepository;
    }

    @Override
    public MarriageApplication saveApplication(MarriageApplication application) {
        if (application.getSubmissionDate() == null) {
            application.setSubmissionDate(LocalDateTime.now());
        }
        if (application.getApplicationStatus() == null) {
            application.setApplicationStatus("PENDING");
        }
        return marriageApplicationRepository.save(application);
    }

    @Override
    public List<MarriageApplication> getAllApplications() {
        return marriageApplicationRepository.findAll();
    }

    @Override
    public MarriageApplication getApplicationById(Long id) {
        Optional<MarriageApplication> application = marriageApplicationRepository.findById(id);
        return application.orElse(null);
    }

    @Override
    public void deleteApplication(Long id) {
        marriageApplicationRepository.deleteById(id);
    }

    @Override
    public MarriageApplication updateApplicationStatus(Long id, String status) {
        Optional<MarriageApplication> applicationOpt = marriageApplicationRepository.findById(id);
        if (applicationOpt.isPresent()) {
            MarriageApplication application = applicationOpt.get();
            application.setApplicationStatus(status);
            return marriageApplicationRepository.save(application);
        }
        return null;
    }
}
