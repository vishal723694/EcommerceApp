<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Wishlist - Online Electronic Store</title>
<link rel="stylesheet" href="images/bootstrap.css">
<style>
    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f8f9fa; }
    .wishlist-container { max-width: 900px; margin: 40px auto; background: #fff; padding: 25px; border-radius: 8px; box-shadow: 0 4px 15px rgba(0,0,0,0.05); }
    .wishlist-title { color: #0d6efd; margin-bottom: 20px; }
</style>
</head>
<body>

<%@ include file="navbar.jsp" %>

<div class="wishlist-container">
    <h2 class="wishlist-title">My Bookmarked Wishlist ❤️</h2>
    <p class="text-muted">Save your favorite electronics here to purchase later.</p>
    
    <table class="table table-bordered align-middle mt-3">
        <thead class="table-dark">
            <tr>
                <th>Product</th>
                <th>Price</th>
                <th>Status</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><b>Samsung Galaxy S22</b></td>
                <td>₹55,000</td>
                <td><span class="badge bg-success">In Stock</span></td>
                <td>
                    <a href="addtocartnull?pid=23" class="btn btn-primary btn-sm">Move to Cart</a>
                    <a href="wishlist?action=remove&pid=23" class="btn btn-outline-danger btn-sm">Remove</a>
                </td>
            </tr>
        </tbody>
    </table>
    
    <a href="viewproduct.jsp" class="btn btn-secondary mt-3">Continue Shopping</a>
</div>

<%@ include file="footer.jsp" %>

</body>
</html>
