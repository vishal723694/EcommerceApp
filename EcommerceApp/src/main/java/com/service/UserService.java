package com.service;

import java.sql.Connection;
import java.util.List;

import com.conn.DBConnect;
import com.dao.AuditLogDAO;
import com.dao.DAO;
import com.dao.UserProfileDAO;
import com.entity.AuditLog;
import com.entity.UserProfile;
import com.entity.customer;
import com.utility.DateUtil;
import com.utility.PasswordUtil;
import com.utility.ValidationUtil;

/**
 * Enterprise Service layer managing user accounts, authentication security,
 * registration validation, password hashing, profile binding, and audit logging.
 */
public class UserService {

    private Connection conn;
    private UserProfileDAO profileDAO;
    private AuditLogDAO auditDAO;

    public UserService() {
        this.conn = DBConnect.getConn();
        this.profileDAO = new UserProfileDAO(conn);
        this.auditDAO = new AuditLogDAO(conn);
    }

    public UserService(Connection conn) {
        this.conn = conn;
        this.profileDAO = new UserProfileDAO(conn);
        this.auditDAO = new AuditLogDAO(conn);
    }

    /**
     * Validates registration details for new customer sign-up.
     *
     * @param name Customer name
     * @param email Customer email
     * @param password Password
     * @param phone Contact number string
     * @return Error message string if invalid, or null if valid
     */
    public String validateRegistrationData(String name, String email, String password, String phone) {
        if (name == null || name.trim().length() < 2) {
            return "Name must be at least 2 characters long.";
        }
        if (email == null || !ValidationUtil.isValidEmail(email)) {
            return "Please provide a valid email address.";
        }
        if (password == null || password.trim().length() < 4) {
            return "Password must be at least 4 characters long.";
        }
        if (phone != null && !phone.isEmpty() && !ValidationUtil.isValidPhone(phone)) {
            return "Please provide a valid contact number.";
        }
        return null;
    }

    /**
     * Checks if email address is already registered in customer database.
     *
     * @param email Target email address
     * @return True if exists
     */
    public boolean isEmailRegistered(String email) {
        if (email == null || email.isEmpty()) return false;
        DAO dao = new DAO(conn);
        List<customer> customers = dao.getCustomer(email);
        return customers != null && !customers.isEmpty();
    }

    /**
     * Registers a new customer user and initializes audit log and profile record.
     *
     * @param name Name
     * @param email Email
     * @param password Password
     * @param phone Phone number
     * @param ipAddress IP address of client
     * @return True if registration succeeded
     */
    public boolean registerCustomerUser(String name, String email, String password, int phone, String ipAddress) {
        try {
            customer c = new customer();
            c.setName(ValidationUtil.sanitizeInput(name));
            c.setEmail_Id(email);
            c.setPassword(PasswordUtil.hashPassword(password));
            c.setContact_No(phone);

            // Save default profile record
            UserProfile profile = new UserProfile(
                email,
                c.getName(),
                String.valueOf(phone),
                "",
                "",
                "",
                "",
                "New registered member"
            );
            profileDAO.saveOrUpdateProfile(profile);

            // Audit log entry
            AuditLog audit = new AuditLog(
                0,
                email,
                "USER_REGISTERED",
                "New account created for " + name,
                ipAddress != null ? ipAddress : "127.0.0.1",
                DateUtil.getCurrentDateTimeFormatted()
            );
            auditDAO.logAction(audit);

            return true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Authenticates customer user with email and password.
     *
     * @param email User email
     * @param plainPassword Raw password
     * @param ipAddress Client IP
     * @return Authenticated customer object or null if failed
     */
    public customer authenticateUser(String email, String plainPassword, String ipAddress) {
        if (email == null || plainPassword == null) {
            return null;
        }

        DAO dao = new DAO(conn);
        List<customer> list = dao.getCustomer(email);

        if (list != null && !list.isEmpty()) {
            customer c = list.get(0);
            String storedPassword = c.getPassword();

            boolean matches = PasswordUtil.verifyPassword(plainPassword, storedPassword)
                              || plainPassword.equals(storedPassword);

            if (matches) {
                // Log successful login audit
                AuditLog audit = new AuditLog(
                    0,
                    email,
                    "LOGIN_SUCCESS",
                    "User logged in successfully",
                    ipAddress != null ? ipAddress : "127.0.0.1",
                    DateUtil.getCurrentDateTimeFormatted()
                );
                auditDAO.logAction(audit);
                return c;
            }
        }

        // Log failed login attempt
        AuditLog audit = new AuditLog(
            0,
            email,
            "LOGIN_FAILED",
            "Invalid credentials attempt",
            ipAddress != null ? ipAddress : "127.0.0.1",
            DateUtil.getCurrentDateTimeFormatted()
        );
        auditDAO.logAction(audit);

        return null;
    }
}
