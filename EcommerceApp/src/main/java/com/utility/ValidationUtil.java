package com.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Utility class providing validation, sanitization, and security helper methods
 * for user inputs, emails, phone numbers, and password hashing.
 * 
 * @author Vishal
 */
public class ValidationUtil {

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    private static final Pattern PHONE_PATTERN = Pattern.compile("^\\d{10}$");

    /**
     * Validates if the provided email string follows a standard email format.
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Validates if the provided string is a valid 10-digit phone number.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            return false;
        }
        return PHONE_PATTERN.matcher(phone.trim()).matches();
    }

    /**
     * Sanitizes user input to prevent HTML/XSS injection attacks.
     */
    public static String sanitizeInput(String input) {
        if (input == null) {
            return "";
        }
        return input.replaceAll("<", "&lt;")
                    .replaceAll(">", "&gt;")
                    .replaceAll("\"", "&quot;")
                    .replaceAll("'", "&#x27;")
                    .replaceAll("&", "&amp;");
    }

    /**
     * Hashes plain text password using SHA-256 algorithm.
     */
    public static String hashPassword(String password) {
        if (password == null || password.isEmpty()) {
            return "";
        }
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return password;
        }
    }
}
