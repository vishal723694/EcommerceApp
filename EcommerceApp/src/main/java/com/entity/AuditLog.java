package com.entity;

/**
 * Entity representing system activity and security audit entries.
 */
public class AuditLog {
    private int auditId;
    private String userEmail;
    private String action;
    private String details;
    private String ipAddress;
    private String timestamp;

    public AuditLog() {
    }

    public AuditLog(int auditId, String userEmail, String action, String details, String ipAddress, String timestamp) {
        this.auditId = auditId;
        this.userEmail = userEmail;
        this.action = action;
        this.details = details;
        this.ipAddress = ipAddress;
        this.timestamp = timestamp;
    }

    public int getAuditId() {
        return auditId;
    }

    public void setAuditId(int auditId) {
        this.auditId = auditId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}

// Refactored commit step: refactor(entity): add IP formatting & action tags to AuditLog entity
