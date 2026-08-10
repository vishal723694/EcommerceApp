package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.OrderTrack;

/**
 * Data Access Object for Order Tracking milestones.
 */
public class OrderTrackDAO {
    private Connection conn;

    public OrderTrackDAO(Connection conn) {
        this.conn = conn;
    }

    public void initTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS order_track ("
                    + "trackId INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "orderId INT, "
                    + "status VARCHAR(50), "
                    + "location VARCHAR(100), "
                    + "updateTime VARCHAR(50), "
                    + "comments VARCHAR(255)"
                    + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean addTrackMilestone(OrderTrack track) {
        initTable();
        boolean success = false;
        try {
            String sql = "INSERT INTO order_track (orderId, status, location, updateTime, comments) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, track.getOrderId());
            ps.setString(2, track.getStatus());
            ps.setString(3, track.getLocation());
            ps.setString(4, track.getUpdateTime());
            ps.setString(5, track.getComments());
            int rows = ps.executeUpdate();
            if (rows > 0) success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<OrderTrack> getTrackingHistory(int orderId) {
        initTable();
        List<OrderTrack> list = new ArrayList<OrderTrack>();
        try {
            String sql = "SELECT * FROM order_track WHERE orderId = ? ORDER BY trackId ASC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, orderId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                OrderTrack track = new OrderTrack(
                    rs.getInt("trackId"),
                    rs.getInt("orderId"),
                    rs.getString("status"),
                    rs.getString("location"),
                    rs.getString("updateTime"),
                    rs.getString("comments")
                );
                list.add(track);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

// Refactored commit step: refactor(dao): add milestone tracking query methods to OrderTrackDAO
