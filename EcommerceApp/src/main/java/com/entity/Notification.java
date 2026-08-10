package com.entity;

/**
 * Entity representing user and administrator system notifications.
 */
public class Notification {
    private int notificationId;
    private String userEmail;
    private String title;
    private String message;
    private boolean isRead;
    private String createdAt;

    public Notification() {
    }

    public Notification(int notificationId, String userEmail, String title, String message, boolean isRead, String createdAt) {
        this.notificationId = notificationId;
        this.userEmail = userEmail;
        this.title = title;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

// Refactored commit step: refactor(entity): add read status toggles to Notification entity
