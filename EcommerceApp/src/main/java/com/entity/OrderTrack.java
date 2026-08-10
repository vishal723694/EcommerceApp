package com.entity;

/**
 * Entity representing order shipment milestones and tracking details.
 */
public class OrderTrack {
    private int trackId;
    private int orderId;
    private String status; // e.g., PLACED, PROCESSING, SHIPPED, OUT_FOR_DELIVERY, DELIVERED
    private String location;
    private String updateTime;
    private String comments;

    public OrderTrack() {
    }

    public OrderTrack(int trackId, int orderId, String status, String location, String updateTime, String comments) {
        this.trackId = trackId;
        this.orderId = orderId;
        this.status = status;
        this.location = location;
        this.updateTime = updateTime;
        this.comments = comments;
    }

    public int getTrackId() {
        return trackId;
    }

    public void setTrackId(int trackId) {
        this.trackId = trackId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
