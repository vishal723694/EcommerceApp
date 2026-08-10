package com.service;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import com.conn.DBConnect;
import com.dao.AuditLogDAO;
import com.entity.AuditLog;
import com.entity.Coupon;
import com.utility.DateUtil;

/**
 * Enterprise Service layer managing promotional coupons, discount calculations,
 * promo code validation, and expiration tracking.
 */
public class CouponService {

    private Connection conn;
    private AuditLogDAO auditDAO;

    public CouponService() {
        this.conn = DBConnect.getConn();
        this.auditDAO = new AuditLogDAO(conn);
    }

    public CouponService(Connection conn) {
        this.conn = conn;
        this.auditDAO = new AuditLogDAO(conn);
    }

    /**
     * Validates a promotional coupon code against minimum order total requirements.
     *
     * @param couponCode Coupon code string
     * @param subtotal Order subtotal
     * @return Discount percentage or amount, or 0.0 if invalid
     */
    public double validateAndCalculateDiscount(String couponCode, double subtotal) {
        if (couponCode == null || couponCode.trim().isEmpty() || subtotal <= 0.0) {
            return 0.0;
        }

        String code = couponCode.trim().toUpperCase();

        if ("SAVE10".equals(code) && subtotal >= 500.0) {
            return subtotal * 0.10; // 10% discount
        } else if ("SAVE20".equals(code) && subtotal >= 2000.0) {
            return subtotal * 0.20; // 20% discount
        } else if ("WELCOME100".equals(code) && subtotal >= 1000.0) {
            return 100.0; // Flat ₹100 discount
        } else if ("FESTIVE500".equals(code) && subtotal >= 5000.0) {
            return 500.0; // Flat ₹500 discount
        }

        return 0.0;
    }

    /**
     * Obtains list of active promotional coupons available for customers.
     *
     * @return List of Coupon entities
     */
    public List<Coupon> getActivePromotionalCoupons() {
        List<Coupon> coupons = new ArrayList<Coupon>();
        coupons.add(new Coupon(1, "SAVE10", 10.0, "PERCENTAGE", 500.0, "2026-12-31", true));
        coupons.add(new Coupon(2, "SAVE20", 20.0, "PERCENTAGE", 2000.0, "2026-12-31", true));
        coupons.add(new Coupon(3, "WELCOME100", 100.0, "FLAT", 1000.0, "2026-12-31", true));
        coupons.add(new Coupon(4, "FESTIVE500", 500.0, "FLAT", 5000.0, "2026-12-31", true));
        return coupons;
    }
}
