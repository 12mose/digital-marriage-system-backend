package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.Document;
import com.ishimwe.digitalmarriagesystem.repository.DocumentRepository;
import com.ishimwe.digitalmarriagesystem.service.DocumentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils;
    private final com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository;
    private final com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository applicationRepository;

    public DocumentServiceImpl(DocumentRepository documentRepository,
                               com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils,
                               com.ishimwe.digitalmarriagesystem.repository.UserRepository userRepository,
                               com.ishimwe.digitalmarriagesystem.repository.MarriageApplicationRepository applicationRepository) {
        this.documentRepository = documentRepository;
        this.securityUtils = securityUtils;
        this.userRepository = userRepository;
        this.applicationRepository = applicationRepository;
    }

    @Override
    public Document saveDocument(Document document) {
        return documentRepository.save(document);
    }

    @Override
    public List<Document> getAllDocuments() {
        String email = securityUtils.getCurrentUserEmail();
        if (securityUtils.hasRole("CITIZEN") && email != null) {
            java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
            if (userOpt.isPresent()) {
                Long userId = userOpt.get().getUserId();
                List<com.ishimwe.digitalmarriagesystem.model.MarriageApplication> apps = applicationRepository.findByApplicant1IdOrApplicant2Id(userId, userId);
                List<Long> appIds = apps.stream().map(com.ishimwe.digitalmarriagesystem.model.MarriageApplication::getApplicationId).collect(java.util.stream.Collectors.toList());
                if (appIds.isEmpty()) return java.util.Collections.emptyList();
                return documentRepository.findByApplicationIdIn(appIds);
            }
        }
        return documentRepository.findAll();
    }

    @Override
    public Document getDocumentById(Long id) {
        java.util.Optional<Document> documentOpt = documentRepository.findById(id);
        if (documentOpt.isPresent()) {
            Document document = documentOpt.get();
            if (securityUtils.hasRole("CITIZEN")) {
                String email = securityUtils.getCurrentUserEmail();
                java.util.Optional<com.ishimwe.digitalmarriagesystem.model.User> userOpt = userRepository.findByEmail(email);
                if (userOpt.isPresent()) {
                    Long userId = userOpt.get().getUserId();
                    com.ishimwe.digitalmarriagesystem.model.MarriageApplication app = applicationRepository.findById(document.getApplicationId()).orElse(null);
                    if (app == null || (!userId.equals(app.getApplicant1Id()) && !userId.equals(app.getApplicant2Id()))) {
                        throw new RuntimeException("Unauthorized access to this document");
                    }
                }
            }
            return document;
        }
        return null;
    }

    @Override
    public void deleteDocument(Long id) {
        documentRepository.deleteById(id);
    }
}
