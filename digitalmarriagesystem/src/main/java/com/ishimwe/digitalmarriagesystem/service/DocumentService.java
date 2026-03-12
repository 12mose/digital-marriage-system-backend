package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.Document;
import java.util.List;

public interface DocumentService {
    Document saveDocument(Document document);
    List<Document> getAllDocuments();
    Document getDocumentById(Long id);
    void deleteDocument(Long id);
}
