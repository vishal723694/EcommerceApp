package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.NotificationDAO;

/**
 * Servlet handling user notification read toggle and notification management.
 */
@WebServlet("/NotificationServlet")
public class NotificationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String idStr = request.getParameter("notificationId");
        if (idStr != null && !idStr.isEmpty()) {
            try {
                int notificationId = Integer.parseInt(idStr);
                NotificationDAO dao = new NotificationDAO(DBConnect.getConn());
                dao.markAsRead(notificationId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("notifications.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
