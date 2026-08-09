package com.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.entity.Coupon;
import com.utility.ValidationUtil;

/**
 * Servlet handling checkout discount coupon application and session store.
 * 
 * @author Vishal
 */
@WebServlet("/applyCoupon")
public class ApplyCouponServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        
        String code = ValidationUtil.sanitizeInput(req.getParameter("couponCode"));
        HttpSession session = req.getSession();

        // Preset promo codes (e.g. SAVE10 -> 10%, FESTIVE20 -> 20%)
        if ("SAVE10".equalsIgnoreCase(code)) {
            Coupon coupon = new Coupon("SAVE10", 10.0, 500.0);
            session.setAttribute("appliedCoupon", coupon);
            session.setAttribute("couponMsg", "Coupon SAVE10 applied! 10% discount.");
        } else if ("FESTIVE20".equalsIgnoreCase(code)) {
            Coupon coupon = new Coupon("FESTIVE20", 20.0, 2000.0);
            session.setAttribute("appliedCoupon", coupon);
            session.setAttribute("couponMsg", "Festive offer! 20% discount applied.");
        } else {
            session.removeAttribute("appliedCoupon");
            session.setAttribute("couponMsg", "Invalid or expired coupon code.");
        }

        resp.sendRedirect("cart.jsp");
    }
}
