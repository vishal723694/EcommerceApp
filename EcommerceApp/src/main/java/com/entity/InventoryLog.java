package com.entity;

/**
 * Entity representing stock change records for inventory auditing.
 */
public class InventoryLog {
    private int logId;
    private int productId;
    private String productName;
    private int quantityChange;
    private String changeType; // e.g., RESTOCK, SALE, RETURN, ADJUSTMENT
    private String timestamp;
    private String operator;

    public InventoryLog() {
    }

    public InventoryLog(int logId, int productId, String productName, int quantityChange, String changeType, String timestamp, String operator) {
        this.logId = logId;
        this.productId = productId;
        this.productName = productName;
        this.quantityChange = quantityChange;
        this.changeType = changeType;
        this.timestamp = timestamp;
        this.operator = operator;
    }

    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getQuantityChange() {
        return quantityChange;
    }

    public void setQuantityChange(int quantityChange) {
        this.quantityChange = quantityChange;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}

// Refactored commit step: refactor(entity): add inventory movement constants to InventoryLog entity
