package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.entity.InventoryLog;

/**
 * Data Access Object for Inventory Log and stock adjustment tracking.
 */
public class InventoryDAO {
    private Connection conn;

    public InventoryDAO(Connection conn) {
        this.conn = conn;
    }

    public void initTable() {
        try {
            String sql = "CREATE TABLE IF NOT EXISTS inventory_log ("
                    + "logId INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "productId INT, "
                    + "productName VARCHAR(100), "
                    + "quantityChange INT, "
                    + "changeType VARCHAR(30), "
                    + "timestamp VARCHAR(50), "
                    + "operator VARCHAR(100)"
                    + ")";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean recordStockAdjustment(InventoryLog log) {
        initTable();
        boolean success = false;
        try {
            String sql = "INSERT INTO inventory_log (productId, productName, quantityChange, changeType, timestamp, operator) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, log.getProductId());
            ps.setString(2, log.getProductName());
            ps.setInt(3, log.getQuantityChange());
            ps.setString(4, log.getChangeType());
            ps.setString(5, log.getTimestamp());
            ps.setString(6, log.getOperator());
            
            int rows = ps.executeUpdate();
            if (rows > 0) {
                // Also update stock in product table
                String updateProductSql = "UPDATE product SET pquantity = pquantity + ? WHERE pid = ?";
                PreparedStatement updatePs = conn.prepareStatement(updateProductSql);
                updatePs.setInt(1, log.getQuantityChange());
                updatePs.setInt(2, log.getProductId());
                updatePs.executeUpdate();
                success = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public List<InventoryLog> getAllLogs() {
        initTable();
        List<InventoryLog> list = new ArrayList<InventoryLog>();
        try {
            String sql = "SELECT * FROM inventory_log ORDER BY logId DESC";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                InventoryLog log = new InventoryLog(
                    rs.getInt("logId"),
                    rs.getInt("productId"),
                    rs.getString("productName"),
                    rs.getInt("quantityChange"),
                    rs.getString("changeType"),
                    rs.getString("timestamp"),
                    rs.getString("operator")
                );
                list.add(log);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
