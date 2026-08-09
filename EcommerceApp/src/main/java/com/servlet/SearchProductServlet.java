package com.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.conn.DBConnect;
import com.dao.DAO;
import com.entity.Product;
import com.utility.ValidationUtil;

/**
 * Servlet handling product keyword search and forwarding results to view page.
 * 
 * @author Vishal
 */
@WebServlet("/searchProduct")
public class SearchProductServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String query = req.getParameter("query");
        String sanitizedQuery = ValidationUtil.sanitizeInput(query);
        
        DAO dao = new DAO(DBConnect.getConn());
        List<Product> searchResults = dao.searchProducts(sanitizedQuery);
        
        req.setAttribute("searchResults", searchResults);
        req.setAttribute("searchQuery", sanitizedQuery);
        req.getRequestDispatcher("viewproduct.jsp").forward(req, resp);
    }
}
