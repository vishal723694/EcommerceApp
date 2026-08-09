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
 * Servlet handling submission of product ratings and review comments.
 * 
 * @author Vishal
 */
@WebServlet("/addReview")
public class AddReviewServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        try {
            int pid = Integer.parseInt(req.getParameter("productId"));
            int rating = Integer.parseInt(req.getParameter("rating"));
            String email = ValidationUtil.sanitizeInput(req.getParameter("email"));
            String comment = ValidationUtil.sanitizeInput(req.getParameter("comment"));
            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

            Connection conn = DBConnect.getConn();
            String sql = "INSERT INTO product_reviews(product_id, customer_email, rating, comment, review_date) VALUES(?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, pid);
            ps.setString(2, email);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.setString(5, currentDate);

            ps.executeUpdate();
            resp.sendRedirect("selecteditem.jsp?pid=" + pid + "&msg=review_added");

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendRedirect("error.jsp");
        }
    }
}
