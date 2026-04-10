package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.MarriageApplication;
import com.ishimwe.digitalmarriagesystem.service.MarriageApplicationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class MarriageApplicationController {

    private final MarriageApplicationService marriageApplicationService;

    public MarriageApplicationController(MarriageApplicationService marriageApplicationService) {
        this.marriageApplicationService = marriageApplicationService;
    }

    @PostMapping
    public MarriageApplication createApplication(@RequestBody MarriageApplication application) {
        return marriageApplicationService.saveApplication(application);
    }

    @GetMapping
    public List<MarriageApplication> getAllApplications(@RequestParam(required = false) String status) {
        if (status != null) {
            return marriageApplicationService.getApplicationsByStatus(status);
        }
        return marriageApplicationService.getAllApplications();
    }

    @GetMapping("/{id}")
    public MarriageApplication getApplication(@PathVariable Long id) {
        return marriageApplicationService.getApplicationById(id);
    }

    @PutMapping("/{id}/status")
    public MarriageApplication updateApplicationStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> payload) {
        String status = payload.getOrDefault("status", "Pending");
        // Title Case normalization
        if (status.length() > 0) {
            status = status.substring(0, 1).toUpperCase() + status.substring(1).toLowerCase();
        }
        return marriageApplicationService.updateApplicationStatus(id, status);
    }

    @PutMapping("/{id}/partner-approve")
    public MarriageApplication approveByPartner(@PathVariable Long id) {
        return marriageApplicationService.approveByPartner(id);
    }

    @DeleteMapping("/{id}")
    public void deleteApplication(@PathVariable Long id) {
        marriageApplicationService.deleteApplication(id);
    }
}
