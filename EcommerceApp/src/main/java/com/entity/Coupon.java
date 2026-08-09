package com.entity;

/**
 * Entity model representing discount promo coupons.
 * 
 * @author Vishal
 */
public class Coupon {
    private int couponId;
    private String couponCode;
    private double discountPercentage;
    private double minOrderAmount;
    private boolean active;

    public Coupon() {}

    public Coupon(String couponCode, double discountPercentage, double minOrderAmount) {
        this.couponCode = couponCode;
        this.discountPercentage = discountPercentage;
        this.minOrderAmount = minOrderAmount;
        this.active = true;
    }

    public int getCouponId() {
        return couponId;
    }

    public void setCouponId(int couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public double getMinOrderAmount() {
        return minOrderAmount;
    }

    public void setMinOrderAmount(double minOrderAmount) {
        this.minOrderAmount = minOrderAmount;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public double calculateDiscount(double subtotal) {
        if (!active || subtotal < minOrderAmount) {
            return 0.0;
        }
        return subtotal * (discountPercentage / 100.0);
    }
}
