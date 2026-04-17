package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import com.ishimwe.digitalmarriagesystem.repository.MarriageRepository;
import com.ishimwe.digitalmarriagesystem.service.MarriageService;
import com.ishimwe.digitalmarriagesystem.exception.ApiException;
import com.ishimwe.digitalmarriagesystem.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarriageServiceImpl implements MarriageService {

    private final MarriageRepository marriageRepository;
    private final com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils;
    private final com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository;

    public MarriageServiceImpl(MarriageRepository marriageRepository, 
                               com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils,
                               com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository) {
        this.marriageRepository = marriageRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
    }

    @Override
    public Marriage saveMarriage(Marriage marriage) {
        if (marriage.getStatus() == null) {
            marriage.setStatus("Active");
        }
        return marriageRepository.save(marriage);
    }

    @Override
    public List<Marriage> getAllMarriages() {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                Long userId = user.get().getUserId();
                return marriageRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
            }
        }
        return marriageRepository.findAll();
    }

    @Override
    public Marriage getMarriageById(Long id) {
        java.util.Optional<Marriage> marriageOpt = marriageRepository.findById(id);
        if (marriageOpt.isPresent()) {
            Marriage marriage = marriageOpt.get();
            if (securityUtils.hasRole("CITIZEN")) {
                String email = securityUtils.getCurrentUserEmail();
                java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> user = userRepository.findByEmail(email);
                if (user.isPresent()) {
                    Long userId = user.get().getUserId();
                    if (!userId.equals(marriage.getApplicant1Id()) && !userId.equals(marriage.getApplicant2Id())) {
                        throw new ApiException("Unauthorized access to this marriage record");
                    }
                }
            }
            return marriage;
        }
        throw new ResourceNotFoundException("Marriage record not found with id: " + id);
    }

    @Override
    public void deleteMarriage(Long id) {
        marriageRepository.deleteById(id);
    }

    @Override
    public Marriage updateMarriageStatus(Long id, String status) {
        Optional<Marriage> marriageOpt = marriageRepository.findById(id);
        if (marriageOpt.isPresent()) {
            Marriage marriage = marriageOpt.get();
            marriage.setStatus(status);
            return marriageRepository.save(marriage);
        }
        throw new ResourceNotFoundException("Marriage record not found with id: " + id);
    }

    @Override
    public List<Marriage> getMarriagesByStatus(String status) {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> user = userRepository.findByEmail(email);
            if (user.isPresent()) {
                Long userId = user.get().getUserId();
                // Filter all user marriages by status manually or add a repo method
                return marriageRepository.findByApplicant1IdOrApplicant2Id(userId, userId).stream()
                        .filter(m -> m.getStatus().equalsIgnoreCase(status))
                        .collect(java.util.stream.Collectors.toList());
            }
        }
        return marriageRepository.findByStatus(status);
    }
}
