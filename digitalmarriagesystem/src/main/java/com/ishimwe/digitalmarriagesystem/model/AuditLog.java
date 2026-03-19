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
    private String userEmail;

    public AuditLog(){}

    public Long getLogId() {
        return logId;
    }

    public void setLogId(Long logId) {
        this.logId = logId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public LocalDateTime getActionDate() {
        return actionDate;
    }

    public void setActionDate(LocalDateTime actionDate) {
        this.actionDate = actionDate;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}