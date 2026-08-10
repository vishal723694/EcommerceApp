<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Something Went Wrong - Ecommerce App</title>
<link rel="stylesheet" href="images/bootstrap.css">
<style>
    body {
        background-color: #f8f9fa;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .error-card {
        max-width: 600px;
        margin: 80px auto;
        padding: 40px;
        background: #ffffff;
        border-radius: 12px;
        box-shadow: 0 10px 25px rgba(0, 0, 0, 0.08);
        text-align: center;
    }
    .error-code {
        font-size: 72px;
        font-weight: 700;
        color: #dc3545;
        margin-bottom: 0;
    }
    .btn-home {
        background-color: #0d6efd;
        color: #fff;
        padding: 10px 24px;
        border-radius: 6px;
        text-decoration: none;
        display: inline-block;
        margin-top: 20px;
    }
    .btn-home:hover {
        background-color: #0b5ed7;
        color: #fff;
    }
</style>
</head>
<body>

<div class="error-card">
    <div class="error-code">Oops!</div>
    <h3 class="mt-3">An Unexpected Error Occurred</h3>
    <p class="text-muted">We couldn't process your request right now. Please return to the homepage or try again later.</p>
    
    <% if (exception != null) { %>
        <div class="alert alert-danger text-start mt-4">
            <small><b>Error Message:</b> <%= exception.getMessage() != null ? exception.getMessage() : exception.toString() %></small>
        </div>
    <% } %>
    
    <a href="index.jsp" class="btn-home">Return to Home</a>
</div>

</body>
</html>

/* Refactored commit step: refactor(jsp): update error.jsp with user-friendly stack trace fallback */
