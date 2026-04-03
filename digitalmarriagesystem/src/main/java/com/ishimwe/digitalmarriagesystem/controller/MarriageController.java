package com.ishimwe.digitalmarriagesystem.controller;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import com.ishimwe.digitalmarriagesystem.service.MarriageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marriages")
public class MarriageController {

    private final MarriageService marriageService;

    public MarriageController(MarriageService marriageService) {
        this.marriageService = marriageService;
    }

    @PostMapping
    public Marriage createMarriage(@RequestBody Marriage marriage) {
        return marriageService.saveMarriage(marriage);
    }

    @GetMapping
    public List<Marriage> getAllMarriages(@RequestParam(required = false) String status) {
        if (status != null) {
            return marriageService.getMarriagesByStatus(status);
        }
        return marriageService.getAllMarriages();
    }

    @GetMapping("/{id}")
    public Marriage getMarriage(@PathVariable Long id) {
        return marriageService.getMarriageById(id);
    }

    @PutMapping("/{id}/status")
    public Marriage updateMarriageStatus(@PathVariable Long id, @RequestBody java.util.Map<String, String> payload) {
        String status = payload.getOrDefault("status", "").toUpperCase();
        return marriageService.updateMarriageStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public void deleteMarriage(@PathVariable Long id) {
        marriageService.deleteMarriage(id);
    }
}
