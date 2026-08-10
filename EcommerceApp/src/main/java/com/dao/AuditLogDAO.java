package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.AuditLog;

/**
 * Data Access Object for recorded audit log trails.
 */
public class AuditLogDAO {
    private Connection conn;

    public AuditLogDAO(Connection conn) {
        this.conn = conn;
    }

    public void initTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS audit_log ("
                    + "auditId INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "userEmail VARCHAR(100), "
                    + "action VARCHAR(100), "
                    + "details VARCHAR(255), "
                    + "ipAddress VARCHAR(50), "
                    + "timestamp VARCHAR(50)"
                    + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean logAction(AuditLog log) {
        initTable();
        boolean success = false;
        try {
            String sql = "INSERT INTO audit_log (userEmail, action, details, ipAddress, timestamp) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, log.getUserEmail());
            ps.setString(2, log.getAction());
            ps.setString(3, log.getDetails());
            ps.setString(4, log.getIpAddress());
            ps.setString(5, log.getTimestamp());
            int i = ps.executeUpdate();
            if (i > 0) success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<AuditLog> getRecentAudits(int limit) {
        initTable();
        List<AuditLog> list = new ArrayList<AuditLog>();
        try {
            String sql = "SELECT * FROM audit_log ORDER BY auditId DESC LIMIT ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, limit);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                AuditLog log = new AuditLog(
                    rs.getInt("auditId"),
                    rs.getString("userEmail"),
                    rs.getString("action"),
                    rs.getString("details"),
                    rs.getString("ipAddress"),
                    rs.getString("timestamp")
                );
                list.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
