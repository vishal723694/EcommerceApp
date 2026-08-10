package com.service;

import java.sql.Connection;
import java.util.List;

import com.conn.DBConnect;
import com.dao.NotificationDAO;
import com.entity.Notification;
import com.utility.DateUtil;

/**
 * Enterprise Service layer managing user notifications, broadcast announcements,
 * order update alerts, and unread notification badge components.
 */
public class NotificationService {

    private Connection conn;
    private NotificationDAO notificationDAO;

    public NotificationService() {
        this.conn = DBConnect.getConn();
        this.notificationDAO = new NotificationDAO(conn);
    }

    public NotificationService(Connection conn) {
        this.conn = conn;
        this.notificationDAO = new NotificationDAO(conn);
    }

    /**
     * Sends an order update alert notification to a customer.
     *
     * @param userEmail Target user email
     * @param orderId Order ID
     * @param newStatus Updated order status
     * @return True if notification sent
     */
    public boolean sendOrderStatusNotification(String userEmail, int orderId, String newStatus) {
        if (userEmail == null || userEmail.isEmpty()) return false;

        String title = "Order #" + orderId + " Update: " + newStatus;
        String message = "Your order #" + orderId + " has been updated to status: " + newStatus + ". Track your shipment for real-time updates.";
        String time = DateUtil.getCurrentDateTimeFormatted();

        Notification n = new Notification(0, userEmail, title, message, false, time);
        return notificationDAO.createNotification(n);
    }

    /**
     * Broadcasts a storewide promotional notification to all registered users.
     *
     * @param title Announcement title
     * @param message Announcement message content
     * @return True if broadcast succeeded
     */
    public boolean broadcastStoreAnnouncement(String title, String message) {
        if (title == null || message == null) return false;
        String time = DateUtil.getCurrentDateTimeFormatted();
        Notification n = new Notification(0, "ALL", title, message, false, time);
        return notificationDAO.createNotification(n);
    }

    /**
     * Obtains list of all notifications for a user.
     *
     * @param userEmail User email
     * @return List of Notification objects
     */
    public List<Notification> getUserNotifications(String userEmail) {
        return notificationDAO.getNotificationsForUser(userEmail);
    }

    /**
     * Marks a specific notification as read.
     *
     * @param notificationId Notification ID
     * @return True if marked read
     */
    public boolean markNotificationAsRead(int notificationId) {
        return notificationDAO.markAsRead(notificationId);
    }

    /**
     * Renders an HTML bell icon badge displaying the unread notification count.
     *
     * @param userEmail User email
     * @return HTML string icon with badge
     */
    public String renderNotificationBellHtml(String userEmail) {
        if (userEmail == null || userEmail.isEmpty()) return "";

        int unread = notificationDAO.getUnreadCount(userEmail);
        StringBuilder sb = new StringBuilder();
        sb.append("<a href='notifications.jsp' class='btn btn-outline-light position-relative mr-2'>");
        sb.append("<i class='fas fa-bell'></i>");
        if (unread > 0) {
            sb.append("<span class='badge badge-danger position-absolute' style='top:-5px; right:-5px;'>")
              .append(unread)
              .append("</span>");
        }
        sb.append("</a>");
        return sb.toString();
    }
}

// Refactored commit step: feat(service): implement NotificationService storewide broadcast messaging
