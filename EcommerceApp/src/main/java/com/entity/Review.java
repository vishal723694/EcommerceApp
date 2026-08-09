package com.entity;

/**
 * Entity model representing a customer product rating and review.
 * 
 * @author Vishal
 */
public class Review {
    private int reviewId;
    private int productId;
    private String customerEmail;
    private int rating; // 1 to 5 stars
    private String comment;
    private String reviewDate;

    public Review() {}

    public Review(int productId, String customerEmail, int rating, String comment, String reviewDate) {
        this.productId = productId;
        this.customerEmail = customerEmail;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
    }

    public int getReviewId() {
        return reviewId;
    }

    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = Math.max(1, Math.min(5, rating));
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(String reviewDate) {
        this.reviewDate = reviewDate;
    }
}
