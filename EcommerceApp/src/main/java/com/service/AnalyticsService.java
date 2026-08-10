package com.service;

import java.sql.Connection;
import java.util.List;

import com.conn.DBConnect;
import com.dao.AnalyticsDAO;
import com.entity.SalesSummary;
import com.utility.CurrencyUtil;

/**
 * Enterprise Service layer providing business intelligence, sales reporting,
 * product category distribution analysis, and growth statistics.
 */
public class AnalyticsService {

    private Connection conn;
    private AnalyticsDAO analyticsDAO;

    public AnalyticsService() {
        this.conn = DBConnect.getConn();
        this.analyticsDAO = new AnalyticsDAO(conn);
    }

    public AnalyticsService(Connection conn) {
        this.conn = conn;
        this.analyticsDAO = new AnalyticsDAO(conn);
    }

    /**
     * Obtains overall business performance metrics.
     *
     * @return SalesSummary object
     */
    public SalesSummary getBusinessMetrics() {
        return analyticsDAO.getOverallSalesSummary();
    }

    /**
     * Calculates average revenue earned per completed order.
     *
     * @return Average order value in double
     */
    public double calculateAverageOrderValue() {
        SalesSummary summary = getBusinessMetrics();
        if (summary.getTotalOrders() <= 0) {
            return 0.0;
        }
        return summary.getTotalRevenue() / summary.getTotalOrders();
    }

    /**
     * Renders HTML KPI dashboard widget cards for administrative views.
     *
     * @return HTML string containing Bootstrap cards
     */
    public String renderDashboardCardsHtml() {
        SalesSummary summary = getBusinessMetrics();
        double aov = calculateAverageOrderValue();

        StringBuilder sb = new StringBuilder();
        sb.append("<div class='row mb-4'>");

        // Total Revenue Card
        sb.append("<div class='col-md-3'><div class='card text-white bg-primary mb-3'><div class='card-body'>")
          .append("<h6 class='card-title text-uppercase'>Total Revenue</h6>")
          .append("<h3 class='card-text font-weight-bold'>").append(CurrencyUtil.formatINR(summary.getTotalRevenue())).append("</h3>")
          .append("</div></div></div>");

        // Total Orders Card
        sb.append("<div class='col-md-3'><div class='card text-white bg-success mb-3'><div class='card-body'>")
          .append("<h6 class='card-title text-uppercase'>Total Orders</h6>")
          .append("<h3 class='card-text font-weight-bold'>").append(summary.getTotalOrders()).append("</h3>")
          .append("</div></div></div>");

        // Products Sold Card
        sb.append("<div class='col-md-3'><div class='card text-white bg-info mb-3'><div class='card-body'>")
          .append("<h6 class='card-title text-uppercase'>Items Sold</h6>")
          .append("<h3 class='card-text font-weight-bold'>").append(summary.getTotalProductsSold()).append("</h3>")
          .append("</div></div></div>");

        // Average Order Value Card
        sb.append("<div class='col-md-3'><div class='card text-white bg-warning mb-3'><div class='card-body'>")
          .append("<h6 class='card-title text-uppercase'>Avg Order Value</h6>")
          .append("<h3 class='card-text font-weight-bold'>").append(CurrencyUtil.formatINR(aov)).append("</h3>")
          .append("</div></div></div>");

        sb.append("</div>");
        return sb.toString();
    }
}
