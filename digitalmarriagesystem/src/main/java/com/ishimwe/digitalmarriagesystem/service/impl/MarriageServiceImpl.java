package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import com.ishimwe.digitalmarriagesystem.repository.MarriageRepository;
import com.ishimwe.digitalmarriagesystem.service.MarriageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MarriageServiceImpl implements MarriageService {

    private final MarriageRepository marriageRepository;

    public MarriageServiceImpl(MarriageRepository marriageRepository) {
        this.marriageRepository = marriageRepository;
    }

    @Override
    public Marriage saveMarriage(Marriage marriage) {
        if (marriage.getMarriageStatus() == null) {
            marriage.setMarriageStatus("ACTIVE");
        }
        return marriageRepository.save(marriage);
    }

    @Override
    public List<Marriage> getAllMarriages() {
        return marriageRepository.findAll();
    }

    @Override
    public Marriage getMarriageById(Long id) {
        Optional<Marriage> marriage = marriageRepository.findById(id);
        return marriage.orElse(null);
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
            marriage.setMarriageStatus(status);
            return marriageRepository.save(marriage);
        }
        return null;
    }
}
