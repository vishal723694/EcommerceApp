<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.conn.DBConnect"%>
<%@ page import="com.dao.UserProfileDAO"%>
<%@ page import="com.entity.UserProfile"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile - Online Electronic Store</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<style>
    body {
        background-color: #f8f9fa;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .profile-card {
        border: none;
        border-radius: 12px;
        box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        background: #fff;
    }
    .avatar-img {
        width: 120px;
        height: 120px;
        border-radius: 50%;
        object-fit: cover;
        border: 4px solid #007bff;
    }
    .btn-custom {
        background: linear-gradient(135deg, #007bff, #0056b3);
        color: white;
        border-radius: 25px;
    }
</style>
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-8">
            <div class="card profile-card p-4">
                <%
                    String userEmail = (String) session.getAttribute("user");
                    UserProfileDAO dao = new UserProfileDAO(DBConnect.getConn());
                    UserProfile profile = dao.getProfileByEmail(userEmail);
                    if (profile == null) {
                        profile = new UserProfile();
                        profile.setEmail(userEmail != null ? userEmail : "");
                    }

                    String msg = (String) session.getAttribute("profileMsg");
                    if (msg != null) {
                %>
                    <div class="alert alert-info alert-dismissible fade show" role="alert">
                        <%= msg %>
                        <button type="button" class="close" data-dismiss="alert">&times;</button>
                    </div>
                <%
                        session.removeAttribute("profileMsg");
                    }
                %>

                <div class="text-center mb-4">
                    <img src="<%= (profile.getAvatarUrl() != null && !profile.getAvatarUrl().isEmpty()) ? profile.getAvatarUrl() : "images/loginimg.png" %>" alt="Profile Avatar" class="avatar-img mb-3">
                    <h3><%= (profile.getFullName() != null && !profile.getFullName().isEmpty()) ? profile.getFullName() : "Customer Profile" %></h3>
                    <p class="text-muted"><i class="fas fa-envelope mr-2"></i><%= profile.getEmail() %></p>
                </div>

                <form action="UserProfileServlet" method="post">
                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label><i class="fas fa-user mr-1"></i> Full Name</label>
                            <input type="text" class="form-control" name="fullName" value="<%= profile.getFullName() != null ? profile.getFullName() : "" %>" placeholder="Enter full name">
                        </div>
                        <div class="form-group col-md-6">
                            <label><i class="fas fa-phone mr-1"></i> Phone Number</label>
                            <input type="text" class="form-control" name="phone" value="<%= profile.getPhone() != null ? profile.getPhone() : "" %>" placeholder="Enter phone number">
                        </div>
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-home mr-1"></i> Shipping Address</label>
                        <input type="text" class="form-control" name="address" value="<%= profile.getAddress() != null ? profile.getAddress() : "" %>" placeholder="Enter full address">
                    </div>

                    <div class="form-row">
                        <div class="form-group col-md-6">
                            <label><i class="fas fa-city mr-1"></i> City</label>
                            <input type="text" class="form-control" name="city" value="<%= profile.getCity() != null ? profile.getCity() : "" %>" placeholder="City">
                        </div>
                        <div class="form-group col-md-6">
                            <label><i class="fas fa-map-pin mr-1"></i> Zip / Postal Code</label>
                            <input type="text" class="form-control" name="zipCode" value="<%= profile.getZipCode() != null ? profile.getZipCode() : "" %>" placeholder="Zip Code">
                        </div>
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-image mr-1"></i> Avatar Image URL</label>
                        <input type="text" class="form-control" name="avatarUrl" value="<%= profile.getAvatarUrl() != null ? profile.getAvatarUrl() : "" %>" placeholder="https://example.com/avatar.jpg">
                    </div>

                    <div class="form-group">
                        <label><i class="fas fa-info-circle mr-1"></i> Bio / Preferences</label>
                        <textarea class="form-control" name="bio" rows="3" placeholder="Tell us about your preferences..."><%= profile.getBio() != null ? profile.getBio() : "" %></textarea>
                    </div>

                    <div class="text-center mt-4">
                        <button type="submit" class="btn btn-custom px-5 py-2 fw-bold"><i class="fas fa-save mr-2"></i>Save Profile Changes</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
