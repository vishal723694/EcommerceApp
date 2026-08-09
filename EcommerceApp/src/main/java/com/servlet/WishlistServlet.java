package com.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.utility.ValidationUtil;

/**
 * Servlet for managing adding and removing items from customer Wishlist.
 * 
 * @author Vishal
 */
@WebServlet("/wishlist")
public class WishlistServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String action = req.getParameter("action");
        String email = ValidationUtil.sanitizeInput(req.getParameter("email"));
        int pid = Integer.parseInt(req.getParameter("pid"));

        try {
            Connection conn = DBConnect.getConn();
            
            if ("add".equalsIgnoreCase(action)) {
                String sql = "INSERT INTO wishlist(customer_email, product_id, added_date) VALUES(?,?,?)";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setInt(2, pid);
                ps.setString(3, new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
                ps.executeUpdate();
            } else if ("remove".equalsIgnoreCase(action)) {
                String sql = "DELETE FROM wishlist WHERE customer_email=? AND product_id=?";
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, email);
                ps.setInt(2, pid);
                ps.executeUpdate();
            }

            resp.sendRedirect("wishlist.jsp?email=" + email);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}
