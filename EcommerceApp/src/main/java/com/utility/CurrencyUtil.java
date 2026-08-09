package com.utility;

import java.text.NumberFormat;
import java.util.Locale;

/**
 * Utility class for formatting currency amounts (INR / USD) and order calculations.
 * 
 * @author Vishal
 */
public class CurrencyUtil {

    private static final Locale INDIA_LOCALE = new Locale("en", "IN");

    /**
     * Formats integer or double price to INR currency format (₹).
     */
    public static String formatINR(double amount) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(INDIA_LOCALE);
        return formatter.format(amount);
    }

    /**
     * Formats integer price with thousand comma separators.
     */
    public static String formatPriceWithCommas(int price) {
        return String.format("%,d", price);
    }

    /**
     * Calculates tax amount (default 18% GST).
     */
    public static double calculateTax(double subtotal, double taxRatePercentage) {
        return subtotal * (taxRatePercentage / 100.0);
    }

    /**
     * Calculates order total including tax and shipping fee.
     */
    public static double calculateGrandTotal(double subtotal, double tax, double shippingFee) {
        return subtotal + tax + shippingFee;
    }
}
