package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.conn.DBConnect;
import com.dao.UserProfileDAO;
import com.entity.UserProfile;
import com.utility.ValidationUtil;

/**
 * Servlet handling user profile updates and preference configuration.
 */
@WebServlet("/UserProfileServlet")
public class UserProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String userEmail = (String) session.getAttribute("user");
        
        if (userEmail == null || userEmail.trim().isEmpty()) {
            response.sendRedirect("customerlogin.jsp");
            return;
        }

        String fullName = ValidationUtil.sanitizeInput(request.getParameter("fullName"));
        String phone = ValidationUtil.sanitizeInput(request.getParameter("phone"));
        String address = ValidationUtil.sanitizeInput(request.getParameter("address"));
        String city = ValidationUtil.sanitizeInput(request.getParameter("city"));
        String zipCode = ValidationUtil.sanitizeInput(request.getParameter("zipCode"));
        String avatarUrl = ValidationUtil.sanitizeInput(request.getParameter("avatarUrl"));
        String bio = ValidationUtil.sanitizeInput(request.getParameter("bio"));

        UserProfile profile = new UserProfile(userEmail, fullName, phone, address, city, zipCode, avatarUrl, bio);
        UserProfileDAO dao = new UserProfileDAO(DBConnect.getConn());
        
        boolean updated = dao.saveOrUpdateProfile(profile);

        if (updated) {
            session.setAttribute("profileMsg", "Profile updated successfully!");
        } else {
            session.setAttribute("profileMsg", "Failed to update profile.");
        }

        response.sendRedirect("profile.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
