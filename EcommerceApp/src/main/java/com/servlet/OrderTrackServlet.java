package com.servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.OrderTrackDAO;
import com.entity.OrderTrack;
import com.utility.DateUtil;

/**
 * Servlet for managing and querying order shipment progress timeline milestones.
 */
@WebServlet("/OrderTrackServlet")
public class OrderTrackServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderIdStr = request.getParameter("orderId");
        if (orderIdStr != null && !orderIdStr.trim().isEmpty()) {
            try {
                int orderId = Integer.parseInt(orderIdStr);
                OrderTrackDAO dao = new OrderTrackDAO(DBConnect.getConn());
                List<OrderTrack> history = dao.getTrackingHistory(orderId);
                request.setAttribute("trackingHistory", history);
                request.setAttribute("searchedOrderId", orderId);
            } catch (NumberFormatException e) {
                request.setAttribute("trackError", "Invalid Order ID format.");
            }
        }
        request.getRequestDispatcher("ordertrack.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Admin update order status milestone
        try {
            int orderId = Integer.parseInt(request.getParameter("orderId"));
            String status = request.getParameter("status");
            String location = request.getParameter("location");
            String comments = request.getParameter("comments");
            String currentTime = DateUtil.getCurrentDateTimeFormatted();

            OrderTrack track = new OrderTrack(0, orderId, status, location, currentTime, comments);
            OrderTrackDAO dao = new OrderTrackDAO(DBConnect.getConn());
            dao.addTrackMilestone(track);

            response.sendRedirect("OrderTrackServlet?orderId=" + orderId);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("ordertrack.jsp?error=Invalid+Input");
        }
    }
}
