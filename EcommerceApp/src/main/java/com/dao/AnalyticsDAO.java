package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.entity.SalesSummary;

/**
 * Data Access Object for generating sales, revenue, and product analytical reports.
 */
public class AnalyticsDAO {
    private Connection conn;

    public AnalyticsDAO(Connection conn) {
        this.conn = conn;
    }

    public SalesSummary getOverallSalesSummary() {
        SalesSummary summary = new SalesSummary(0.0, 0, 0, "N/A", "All-Time");
        try {
            // Calculate total revenue and total orders
            String orderSql = "SELECT COUNT(*) as totalOrders, SUM(Total_Price) as totalRevenue FROM orders";
            PreparedStatement ps1 = conn.prepareStatement(orderSql);
            ResultSet rs1 = ps1.executeQuery();
            if (rs1.next()) {
                summary.setTotalOrders(rs1.getInt("totalOrders"));
                summary.setTotalRevenue(rs1.getDouble("totalRevenue"));
            }

            // Calculate total products sold
            String detailSql = "SELECT SUM(pquantity) as totalQuantity FROM order_details";
            PreparedStatement ps2 = conn.prepareStatement(detailSql);
            ResultSet rs2 = ps2.executeQuery();
            if (rs2.next()) {
                summary.setTotalProductsSold(rs2.getInt("totalQuantity"));
            }

            // Find top selling category
            String catSql = "SELECT cname, SUM(pquantity) as totalQty FROM order_details GROUP BY cname ORDER BY totalQty DESC LIMIT 1";
            PreparedStatement ps3 = conn.prepareStatement(catSql);
            ResultSet rs3 = ps3.executeQuery();
            if (rs3.next()) {
                summary.setTopSellingCategory(rs3.getString("cname"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return summary;
    }
}

// Refactored commit step: refactor(dao): add revenue & category aggregation queries to AnalyticsDAO
