package com.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.conn.DBConnect;
import com.dao.DAO;
import com.dao.InventoryDAO;
import com.entity.InventoryLog;
import com.entity.Product;
import com.utility.DateUtil;

/**
 * Enterprise Service layer managing inventory stock monitoring, batch restocking,
 * low-stock threshold alerting, and inventory audit trail logs.
 */
public class InventoryService {

    private Connection conn;
    private InventoryDAO inventoryDAO;

    public InventoryService() {
        this.conn = DBConnect.getConn();
        this.inventoryDAO = new InventoryDAO(conn);
    }

    public InventoryService(Connection conn) {
        this.conn = conn;
        this.inventoryDAO = new InventoryDAO(conn);
    }

    /**
     * Checks stock level of a product and flags low stock alert.
     * Standard threshold is 5 units.
     *
     * @param product Product entity to evaluate
     * @param threshold Minimum quantity threshold
     * @return True if product stock is below or equal to threshold
     */
    public boolean isLowStock(Product product, int threshold) {
        if (product == null) {
            return false;
        }
        return product.getPquantity() <= threshold;
    }

    /**
     * Retrieves all products currently running below low stock threshold.
     *
     * @param threshold Threshold count
     * @return List of low stock products
     */
    public List<Product> getLowStockProducts(int threshold) {
        List<Product> lowStockList = new ArrayList<Product>();
        try {
            DAO dao = new DAO(conn);
            List<Product> allProducts = dao.searchProducts("");
            for (Product p : allProducts) {
                if (p.getPquantity() <= threshold) {
                    lowStockList.add(p);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lowStockList;
    }

    /**
     * Performs batch restock for a product and logs the inventory adjustment.
     *
     * @param productId Target product ID
     * @param productName Target product name
     * @param restockQty Quantity to add
     * @param operator Name/ID of administrator performing restock
     * @return True if restock succeeded
     */
    public boolean performBatchRestock(int productId, String productName, int restockQty, String operator) {
        if (restockQty <= 0) {
            return false;
        }

        String currentTime = DateUtil.getCurrentDateTimeFormatted();
        InventoryLog log = new InventoryLog(
            0,
            productId,
            productName,
            restockQty,
            "RESTOCK",
            currentTime,
            operator != null ? operator : "System Admin"
        );

        return inventoryDAO.recordStockAdjustment(log);
    }

    /**
     * Calculates total physical stock quantity across all products in inventory.
     *
     * @return Total units count in warehouse
     */
    public int calculateTotalWarehouseUnits() {
        int total = 0;
        try {
            DAO dao = new DAO(conn);
            List<Product> products = dao.searchProducts("");
            for (Product p : products) {
                total += p.getPquantity();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return total;
    }

    /**
     * Calculates total inventory monetary value based on product prices and quantities.
     *
     * @return Total valuation amount
     */
    public double calculateTotalInventoryValue() {
        double totalValuation = 0.0;
        try {
            DAO dao = new DAO(conn);
            List<Product> products = dao.searchProducts("");
            for (Product p : products) {
                totalValuation += (p.getPprice() * p.getPquantity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return totalValuation;
    }

    /**
     * Renders HTML warning badge for low stock items.
     *
     * @param qty Current quantity
     * @return HTML string badge component
     */
    public String renderStockBadgeHtml(int qty) {
        if (qty <= 0) {
            return "<span class='badge badge-danger px-3 py-1'>OUT OF STOCK</span>";
        } else if (qty <= 5) {
            return "<span class='badge badge-warning px-3 py-1'>LOW STOCK (" + qty + " left)</span>";
        } else {
            return "<span class='badge badge-success px-3 py-1'>IN STOCK (" + qty + ")</span>";
        }
    }
}

// Refactored commit step: feat(service): implement InventoryService low-stock threshold alerting
