package com.utility;

import java.util.List;

import com.entity.AuditLog;
import com.entity.InventoryLog;
import com.entity.SalesSummary;
import com.entity.customer;

/**
 * Report generation utility producing CSV, HTML, and formatted text documents
 * for sales analytics, stock audits, security activity, and customer lists.
 */
public class ReportExporter {

    /**
     * Exports Sales Summary data into CSV format string.
     *
     * @param summary Sales Summary DTO
     * @return Formatted CSV string
     */
    public static String exportSalesSummaryToCSV(SalesSummary summary) {
        StringBuilder csv = new StringBuilder();
        csv.append("Metric,Value\n");
        csv.append("Total Revenue,").append(summary.getTotalRevenue()).append("\n");
        csv.append("Total Orders,").append(summary.getTotalOrders()).append("\n");
        csv.append("Total Products Sold,").append(summary.getTotalProductsSold()).append("\n");
        csv.append("Top Selling Category,").append(summary.getTopSellingCategory()).append("\n");
        csv.append("Period,").append(summary.getPeriod()).append("\n");
        return csv.toString();
    }

    /**
     * Exports Inventory Logs list into CSV format string.
     *
     * @param logs List of InventoryLog entities
     * @return Formatted CSV string
     */
    public static String exportInventoryLogsToCSV(List<InventoryLog> logs) {
        StringBuilder csv = new StringBuilder();
        csv.append("Log ID,Product ID,Product Name,Quantity Change,Change Type,Timestamp,Operator\n");
        if (logs != null) {
            for (InventoryLog log : logs) {
                csv.append(log.getLogId()).append(",")
                   .append(log.getProductId()).append(",")
                   .append("\"").append(log.getProductName().replace("\"", "\"\"")).append("\",")
                   .append(log.getQuantityChange()).append(",")
                   .append(log.getChangeType()).append(",")
                   .append(log.getTimestamp()).append(",")
                   .append("\"").append(log.getOperator().replace("\"", "\"\"")).append("\"\n");
            }
        }
        return csv.toString();
    }

    /**
     * Exports Security Audit Logs list into CSV format string.
     *
     * @param audits List of AuditLog entities
     * @return Formatted CSV string
     */
    public static String exportAuditLogsToCSV(List<AuditLog> audits) {
        StringBuilder csv = new StringBuilder();
        csv.append("Audit ID,User Email,Action,Details,IP Address,Timestamp\n");
        if (audits != null) {
            for (AuditLog audit : audits) {
                csv.append(audit.getAuditId()).append(",")
                   .append(audit.getUserEmail()).append(",")
                   .append("\"").append(audit.getAction().replace("\"", "\"\"")).append("\",")
                   .append("\"").append(audit.getDetails().replace("\"", "\"\"")).append("\",")
                   .append(audit.getIpAddress()).append(",")
                   .append(audit.getTimestamp()).append("\n");
            }
        }
        return csv.toString();
    }

    /**
     * Exports customer roster into CSV format string.
     *
     * @param customers List of customer entities
     * @return Formatted CSV string
     */
    public static String exportCustomerListToCSV(List<customer> customers) {
        StringBuilder csv = new StringBuilder();
        csv.append("Name,Email ID,Contact No\n");
        if (customers != null) {
            for (customer c : customers) {
                csv.append("\"").append(c.getName().replace("\"", "\"\"")).append("\",")
                   .append(c.getEmail_Id()).append(",")
                   .append(c.getContact_No()).append("\n");
            }
        }
        return csv.toString();
    }
}

// Refactored commit step: feat(utility): implement ReportExporter CSV report generators for sales & stock
