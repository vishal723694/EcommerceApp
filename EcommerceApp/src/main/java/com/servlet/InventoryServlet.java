package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.InventoryDAO;
import com.entity.InventoryLog;
import com.utility.DateUtil;

/**
 * Servlet handling product stock replenishment and inventory log auditing.
 */
@WebServlet("/InventoryServlet")
public class InventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int productId = Integer.parseInt(request.getParameter("productId"));
            String productName = request.getParameter("productName");
            int quantityChange = Integer.parseInt(request.getParameter("quantityChange"));
            String changeType = request.getParameter("changeType"); // RESTOCK, ADJUSTMENT, DAMAGE
            String operator = request.getParameter("operator");

            if (operator == null || operator.isEmpty()) {
                operator = "Admin";
            }

            InventoryLog log = new InventoryLog(0, productId, productName, quantityChange, changeType, DateUtil.getCurrentDateTimeFormatted(), operator);
            InventoryDAO dao = new InventoryDAO(DBConnect.getConn());
            boolean success = dao.recordStockAdjustment(log);

            if (success) {
                response.sendRedirect("admin.jsp?msg=Inventory+updated+successfully");
            } else {
                response.sendRedirect("admin.jsp?error=Failed+to+update+inventory");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin.jsp?error=Invalid+inventory+params");
        }
    }
}
