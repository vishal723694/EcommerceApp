<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="card mt-4 shadow-sm">
    <div class="card-header bg-light">
        <h4 class="mb-0">Customer Ratings & Reviews ⭐</h4>
    </div>
    <div class="card-body">
        <form action="addReview" method="post" class="mb-4">
            <input type="hidden" name="productId" value="<%= request.getParameter("pid") != null ? request.getParameter("pid") : "1" %>">
            <div class="row g-2 mb-2">
                <div class="col-md-6">
                    <input type="email" name="email" class="form-control" placeholder="Your Email Address" required>
                </div>
                <div class="col-md-6">
                    <select name="rating" class="form-select" required>
                        <option value="5">⭐⭐⭐⭐⭐ (5/5 Excellent)</option>
                        <option value="4">⭐⭐⭐⭐ (4/5 Very Good)</option>
                        <option value="3">⭐⭐⭐ (3/5 Average)</option>
                        <option value="2">⭐⭐ (2/5 Poor)</option>
                        <option value="1">⭐ (1/5 Very Bad)</option>
                    </select>
                </div>
            </div>
            <div class="mb-2">
                <textarea name="comment" class="form-control" rows="3" placeholder="Write your review experience..." required></textarea>
            </div>
            <button type="submit" class="btn btn-success btn-sm">Submit Review</button>
        </form>
        
        <hr>
        
        <div class="review-item p-3 border-bottom">
            <div class="d-flex justify-content-between">
                <strong>vishal@example.com</strong>
                <span class="text-warning">⭐⭐⭐⭐⭐</span>
            </div>
            <small class="text-muted">10 Aug 2026</small>
            <p class="mb-0 mt-1">Excellent product quality and super fast delivery!</p>
        </div>
    </div>
</div>

/* Refactored commit step: refactor(jsp): improve review star ratings UI component in reviews.jsp */
