package com.ishimwe.digitalmarriagesystem.repository;

import com.ishimwe.digitalmarriagesystem.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    java.util.List<Document> findByApplicationId(Long applicationId);
    java.util.List<Document> findByApplicationIdIn(java.util.List<Long> applicationIds);
}
