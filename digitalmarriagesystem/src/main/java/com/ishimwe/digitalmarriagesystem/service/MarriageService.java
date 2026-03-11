package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import java.util.List;

public interface MarriageService {

    Marriage saveMarriage(Marriage marriage);

    List<Marriage> getAllMarriages();

    Marriage getMarriageById(Long id);

    void deleteMarriage(Long id);

    Marriage updateMarriageStatus(Long id, String status);
}
