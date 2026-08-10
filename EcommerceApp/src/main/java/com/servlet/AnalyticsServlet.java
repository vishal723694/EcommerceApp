package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.AnalyticsDAO;
import com.entity.SalesSummary;
import com.utility.CurrencyUtil;

/**
 * Servlet producing store analytics and JSON reports for administrative dashboards.
 */
@WebServlet("/AnalyticsServlet")
public class AnalyticsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        AnalyticsDAO dao = new AnalyticsDAO(DBConnect.getConn());
        SalesSummary summary = dao.getOverallSalesSummary();

        PrintWriter out = response.getWriter();
        String jsonResponse = String.format(
            "{\"totalRevenue\": \"%s\", \"totalOrders\": %d, \"totalProductsSold\": %d, \"topSellingCategory\": \"%s\"}",
            CurrencyUtil.formatINR(summary.getTotalRevenue()),
            summary.getTotalOrders(),
            summary.getTotalProductsSold(),
            summary.getTopSellingCategory()
        );

        out.print(jsonResponse);
        out.flush();
    }
}
