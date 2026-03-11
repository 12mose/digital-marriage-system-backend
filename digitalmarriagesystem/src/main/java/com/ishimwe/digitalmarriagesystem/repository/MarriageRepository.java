package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarriageRepository extends JpaRepository<Marriage, Long> {
}