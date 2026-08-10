package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.AnalyticsDAO;
import com.dao.AuditLogDAO;
import com.dao.DAO;
import com.dao.InventoryDAO;
import com.entity.AuditLog;
import com.entity.InventoryLog;
import com.entity.SalesSummary;
import com.entity.customer;
import com.utility.ReportExporter;

/**
 * Servlet handling downloading CSV report files for sales summaries, inventory logs,
 * security audit logs, and customer rosters.
 */
@WebServlet("/ExportReportServlet")
public class ExportReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String reportType = request.getParameter("type");
        if (reportType == null || reportType.isEmpty()) {
            reportType = "sales";
        }

        response.setContentType("text/csv");

        if ("inventory".equalsIgnoreCase(reportType)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"inventory_report.csv\"");
            InventoryDAO dao = new InventoryDAO(DBConnect.getConn());
            List<InventoryLog> logs = dao.getAllLogs();
            String csvData = ReportExporter.exportInventoryLogsToCSV(logs);
            PrintWriter out = response.getWriter();
            out.print(csvData);
            out.flush();

        } else if ("audit".equalsIgnoreCase(reportType)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"audit_logs.csv\"");
            AuditLogDAO dao = new AuditLogDAO(DBConnect.getConn());
            List<AuditLog> audits = dao.getRecentAudits(1000);
            String csvData = ReportExporter.exportAuditLogsToCSV(audits);
            PrintWriter out = response.getWriter();
            out.print(csvData);
            out.flush();

        } else if ("customers".equalsIgnoreCase(reportType)) {
            response.setHeader("Content-Disposition", "attachment; filename=\"customer_roster.csv\"");
            DAO dao = new DAO(DBConnect.getConn());
            List<customer> customers = dao.getAllCustomer();
            String csvData = ReportExporter.exportCustomerListToCSV(customers);
            PrintWriter out = response.getWriter();
            out.print(csvData);
            out.flush();

        } else {
            response.setHeader("Content-Disposition", "attachment; filename=\"sales_summary.csv\"");
            AnalyticsDAO dao = new AnalyticsDAO(DBConnect.getConn());
            SalesSummary summary = dao.getOverallSalesSummary();
            String csvData = ReportExporter.exportSalesSummaryToCSV(summary);
            PrintWriter out = response.getWriter();
            out.print(csvData);
            out.flush();
        }
    }
}

// Refactored commit step: feat(servlet): add CSV export endpoint in ExportReportServlet
