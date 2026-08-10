package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.Notification;

/**
 * Data Access Object for handling User and Administrator System Notifications.
 */
public class NotificationDAO {
    private Connection conn;

    public NotificationDAO(Connection conn) {
        this.conn = conn;
    }

    /**
     * Initializes notifications table if not existing.
     */
    public void initTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS notification ("
                    + "notificationId INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "userEmail VARCHAR(100), "
                    + "title VARCHAR(150), "
                    + "message VARCHAR(500), "
                    + "isRead INT DEFAULT 0, "
                    + "createdAt VARCHAR(50)"
                    + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Inserts a new notification message.
     *
     * @param notification Notification entity
     * @return True if inserted successfully
     */
    public boolean createNotification(Notification notification) {
        initTable();
        boolean success = false;
        try {
            String sql = "INSERT INTO notification (userEmail, title, message, isRead, createdAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, notification.getUserEmail());
            ps.setString(2, notification.getTitle());
            ps.setString(3, notification.getMessage());
            ps.setInt(4, notification.isRead() ? 1 : 0);
            ps.setString(5, notification.getCreatedAt());
            int i = ps.executeUpdate();
            if (i > 0) success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Retrieves all notifications for a specific user email.
     *
     * @param userEmail User email
     * @return List of notifications
     */
    public List<Notification> getNotificationsForUser(String userEmail) {
        initTable();
        List<Notification> list = new ArrayList<Notification>();
        try {
            String sql = "SELECT * FROM notification WHERE userEmail = ? OR userEmail = 'ALL' ORDER BY notificationId DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Notification n = new Notification(
                    rs.getInt("notificationId"),
                    rs.getString("userEmail"),
                    rs.getString("title"),
                    rs.getString("message"),
                    rs.getInt("isRead") == 1,
                    rs.getString("createdAt")
                );
                list.add(n);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Marks a notification as read.
     *
     * @param notificationId Target notification ID
     * @return True if updated
     */
    public boolean markAsRead(int notificationId) {
        initTable();
        boolean success = false;
        try {
            String sql = "UPDATE notification SET isRead = 1 WHERE notificationId = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, notificationId);
            int i = ps.executeUpdate();
            if (i > 0) success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    /**
     * Gets unread notifications count for a user.
     *
     * @param userEmail User email
     * @return Unread count
     */
    public int getUnreadCount(String userEmail) {
        initTable();
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM notification WHERE (userEmail = ? OR userEmail = 'ALL') AND isRead = 0";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, userEmail);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }
}
