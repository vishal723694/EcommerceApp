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
import com.dao.DAO;
import com.entity.Product;

/**
 * REST API Servlet exposing products list as JSON endpoint at /api/products.
 * 
 * @author Vishal
 */
@WebServlet("/api/products")
public class ProductApiServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        
        DAO dao = new DAO(DBConnect.getConn());
        List<Product> products = dao.searchProducts(""); // get all or filter
        
        PrintWriter out = resp.getWriter();
        StringBuilder json = new StringBuilder();
        json.append("[\n");
        
        for (int i = 0; i < products.size(); i++) {
            Product p = products.get(i);
            json.append("  {\n");
            json.append("    \"id\": ").append(p.getPid()).append(",\n");
            json.append("    \"name\": \"").append(escapeJson(p.getPname())).append("\",\n");
            json.append("    \"price\": ").append(p.getPprice()).append(",\n");
            json.append("    \"quantity\": ").append(p.getPquantity()).append(",\n");
            json.append("    \"image\": \"").append(escapeJson(p.getPimage())).append("\"\n");
            json.append("  }").append(i < products.size() - 1 ? "," : "").append("\n");
        }
        
        json.append("]");
        out.print(json.toString());
        out.flush();
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\b", "\\b")
                    .replace("\f", "\\f")
                    .replace("\n", "\\n")
                    .replace("\r", "\\r")
                    .replace("\t", "\\t");
    }
}
