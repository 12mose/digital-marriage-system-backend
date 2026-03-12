package com.ishimwe.digitalmarriagesystem.service;

import com.ishimwe.digitalmarriagesystem.model.AuditLog;
import java.util.List;

public interface AuditLogService {
    AuditLog saveAuditLog(AuditLog auditLog);
    List<AuditLog> getAllAuditLogs();
    AuditLog getAuditLogById(Long id);
    void deleteAuditLog(Long id);
}
