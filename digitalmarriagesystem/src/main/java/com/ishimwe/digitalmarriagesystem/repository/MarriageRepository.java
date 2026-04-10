package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.Marriage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface MarriageRepository extends JpaRepository<Marriage, Long> {
    List<Marriage> findByStatus(String status);

    @Query("SELECT COUNT(m) > 0 FROM Marriage m WHERE (m.applicant1Id = :id OR m.applicant2Id = :id) AND m.status = 'Active'")
    boolean isUserAlreadyMarried(@Param("id") Long id);
}