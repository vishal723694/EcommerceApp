package com.service;

import java.sql.Connection;
import com.conn.DBConnect;
import com.dao.AuditLogDAO;
import com.entity.AuditLog;
import com.utility.DateUtil;
import com.utility.ValidationUtil;

/**
 * Enterprise Service layer managing payment transaction validation, card security checks,
 * payment gateway simulation, and payment audit logging.
 */
public class PaymentService {

    private Connection conn;
    private AuditLogDAO auditDAO;

    public PaymentService() {
        this.conn = DBConnect.getConn();
        this.auditDAO = new AuditLogDAO(conn);
    }

    public PaymentService(Connection conn) {
        this.conn = conn;
        this.auditDAO = new AuditLogDAO(conn);
    }

    /**
     * Validates payment card details for checkout.
     *
     * @param cardNumber Card number string
     * @param expiry Expiry date (MM/YY)
     * @param cvv CVV code
     * @return True if valid format
     */
    public boolean validateCardDetails(String cardNumber, String expiry, String cvv) {
        if (cardNumber == null || expiry == null || cvv == null) {
            return false;
        }

        String cleanCard = cardNumber.replaceAll("\\s|-", "");
        if (cleanCard.length() < 13 || cleanCard.length() > 19 || !cleanCard.matches("\\d+")) {
            return false;
        }

        if (!cvv.matches("\\d{3,4}")) {
            return false;
        }

        if (!expiry.matches("(0[1-9]|1[0-2])\\/([0-9]{2})")) {
            return false;
        }

        return true;
    }

    /**
     * Processes simulated online payment transaction.
     *
     * @param userEmail Customer email
     * @param amount Amount to charge
     * @param paymentMethod Payment method (CARD, UPI, COD, NETBANKING)
     * @return True if payment approved
     */
    public boolean processPaymentTransaction(String userEmail, double amount, String paymentMethod) {
        if (amount <= 0.0) {
            return false;
        }

        boolean approved = true;

        AuditLog audit = new AuditLog(
            0,
            userEmail != null ? userEmail : "GUEST",
            "PAYMENT_PROCESSED",
            "Payment of ₹" + amount + " via " + paymentMethod + " - Approved: " + approved,
            "127.0.0.1",
            DateUtil.getCurrentDateTimeFormatted()
        );

        auditDAO.logAction(audit);

        return approved;
    }
}

// Refactored commit step: feat(service): implement PaymentService card security & gateway simulation
