package com.ishimwe.digitalmarriagesystem.service.impl;

import com.ishimwe.digitalmarriagesystem.model.AuditLog;
import com.ishimwe.digitalmarriagesystem.repository.AuditLogRepository;
import com.ishimwe.digitalmarriagesystem.service.AuditLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuditLogServiceImpl implements AuditLogService {

    private final AuditLogRepository auditLogRepository;
    private final com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils;

    public AuditLogServiceImpl(AuditLogRepository auditLogRepository, com.ishimwe.digitalmarriagesystem.security.SecurityUtils securityUtils) {
        this.auditLogRepository = auditLogRepository;
        this.securityUtils = securityUtils;
    }

    @Override
    public AuditLog saveAuditLog(AuditLog auditLog) {
        if (auditLog.getActionDate() == null) {
            auditLog.setActionDate(java.time.LocalDateTime.now());
        }
        if (auditLog.getUserEmail() == null) {
            auditLog.setUserEmail(securityUtils.getCurrentUserEmail());
        }
        return auditLogRepository.save(auditLog);
    }

    @Override
    public List<AuditLog> getAllAuditLogs() {
        return auditLogRepository.findAll();
    }

    @Override
    public AuditLog getAuditLogById(Long id) {
        return auditLogRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteAuditLog(Long id) {
        auditLogRepository.deleteById(id);
    }
}
