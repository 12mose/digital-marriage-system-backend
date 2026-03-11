package com.ishimwe.digitalmarriagesystem.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="audit_log")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    private String action;
    private LocalDateTime actionDate;

    public AuditLog(){}
}