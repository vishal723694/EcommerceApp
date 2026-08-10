package com.entity;

/**
 * DTO representing aggregated store sales metrics for administrative reports.
 */
public class SalesSummary {
    private double totalRevenue;
    private int totalOrders;
    private int totalProductsSold;
    private String topSellingCategory;
    private String period;

    public SalesSummary() {
    }

    public SalesSummary(double totalRevenue, int totalOrders, int totalProductsSold, String topSellingCategory, String period) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.totalProductsSold = totalProductsSold;
        this.topSellingCategory = topSellingCategory;
        this.period = period;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalOrders() {
        return totalOrders;
    }

    public void setTotalOrders(int totalOrders) {
        this.totalOrders = totalOrders;
    }

    public int getTotalProductsSold() {
        return totalProductsSold;
    }

    public void setTotalProductsSold(int totalProductsSold) {
        this.totalProductsSold = totalProductsSold;
    }

    public String getTopSellingCategory() {
        return topSellingCategory;
    }

    public void setTopSellingCategory(String topSellingCategory) {
        this.topSellingCategory = topSellingCategory;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }
}
