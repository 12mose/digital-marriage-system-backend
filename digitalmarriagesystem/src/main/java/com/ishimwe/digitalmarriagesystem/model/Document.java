package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;

@Entity
@Table(name="document")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long documentId;

    private String documentType;
    private String filePath;
    private Long applicationId;

    public Document(){}
}