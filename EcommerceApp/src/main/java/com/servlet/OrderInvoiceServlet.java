package com.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.utility.CurrencyUtil;
import com.utility.DateUtil;

/**
 * Servlet generating clean printable HTML invoice for completed customer orders.
 * 
 * @author Vishal
 */
@WebServlet("/orderInvoice")
public class OrderInvoiceServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();

        String orderId = req.getParameter("orderId");
        String customer = req.getParameter("customer");
        String totalStr = req.getParameter("total");
        double total = totalStr != null ? Double.parseDouble(totalStr) : 0.0;

        out.println("<!DOCTYPE html><html><head><title>Invoice #" + orderId + "</title>");
        out.println("<style>body{font-family:Arial,sans-serif;padding:30px;}.box{border:1px solid #ccc;padding:20px;max-width:600px;margin:auto;}h2{color:#0d6efd;}</style>");
        out.println("</head><body>");
        out.println("<div class='box'>");
        out.println("<h2>Online Electronic Store - Order Invoice</h2><hr>");
        out.println("<p><b>Invoice No:</b> INV-" + (orderId != null ? orderId : "1001") + "</p>");
        out.println("<p><b>Date:</b> " + DateUtil.getCurrentFormattedDate() + "</p>");
        out.println("<p><b>Customer Name:</b> " + (customer != null ? customer : "Valued Customer") + "</p>");
        out.println("<hr><table width='100%' border='1' cellpadding='8' cellspacing='0'>");
        out.println("<tr><th>Description</th><th>Amount</th></tr>");
        out.println("<tr><td>Electronics Order #" + orderId + "</td><td>" + CurrencyUtil.formatINR(total) + "</td></tr>");
        out.println("<tr><td><b>Grand Total</b></td><td><b>" + CurrencyUtil.formatINR(total) + "</b></td></tr>");
        out.println("</table><br><center><button onclick='window.print()'>Print Invoice</button></center>");
        out.println("</div></body></html>");
    }
}
