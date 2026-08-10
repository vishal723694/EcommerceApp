<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List"%>
<%@ page import="com.entity.OrderTrack"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Order Tracking - Online Electronic Store</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.4/css/all.min.css">
<style>
    body {
        background-color: #f4f7f6;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
    }
    .track-card {
        border: none;
        border-radius: 15px;
        box-shadow: 0 4px 20px rgba(0,0,0,0.08);
        background: #fff;
    }
    .timeline {
        list-style-type: none;
        position: relative;
        padding-left: 30px;
    }
    .timeline:before {
        content: ' ';
        background: #d4d9df;
        display: inline-block;
        position: absolute;
        left: 9px;
        width: 2px;
        height: 100%;
        z-index: 400;
    }
    .timeline > li {
        margin: 20px 0;
        padding-left: 20px;
    }
    .timeline > li:before {
        content: ' ';
        background: white;
        display: inline-block;
        position: absolute;
        border-radius: 50%;
        border: 3px solid #007bff;
        left: 0px;
        width: 20px;
        height: 20px;
        z-index: 400;
    }
    .timeline-badge-completed:before {
        border-color: #28a745 !important;
        background: #28a745 !important;
    }
</style>
</head>
<body>
<%@ include file="navbar.jsp" %>

<div class="container my-5">
    <div class="row justify-content-center">
        <div class="col-md-9">
            <div class="card track-card p-4">
                <h2 class="text-center font-weight-bold text-primary mb-4"><i class="fas fa-truck-loading mr-2"></i>Track Your Shipment</h2>
                
                <form action="OrderTrackServlet" method="get" class="mb-4">
                    <div class="input-group">
                        <input type="number" class="form-control form-control-lg" name="orderId" placeholder="Enter your Order ID (e.g. 101)" required value="<%= request.getAttribute("searchedOrderId") != null ? request.getAttribute("searchedOrderId") : "" %>">
                        <div class="input-group-append">
                            <button type="submit" class="btn btn-primary px-4"><i class="fas fa-search mr-1"></i> Track Order</button>
                        </div>
                    </div>
                </form>

                <%
                    List<OrderTrack> history = (List<OrderTrack>) request.getAttribute("trackingHistory");
                    Integer searchedOrderId = (Integer) request.getAttribute("searchedOrderId");
                    if (searchedOrderId != null) {
                %>
                    <div class="border-top pt-4">
                        <h4 class="mb-3">Tracking History for Order #<%= searchedOrderId %></h4>
                        <% if (history != null && !history.isEmpty()) { %>
                            <ul class="timeline">
                                <% for (OrderTrack item : history) { %>
                                    <li class="timeline-item">
                                        <h5 class="font-weight-bold text-dark"><%= item.getStatus() %></h5>
                                        <p class="text-muted mb-1"><i class="fas fa-map-marker-alt text-danger mr-1"></i> <%= item.getLocation() %> | <i class="far fa-clock text-info mr-1"></i> <%= item.getUpdateTime() %></p>
                                        <p class="mb-0"><%= item.getComments() != null ? item.getComments() : "" %></p>
                                    </li>
                                <% } %>
                            </ul>
                        <% } else { %>
                            <div class="alert alert-warning text-center">
                                <i class="fas fa-exclamation-circle mr-2"></i> No tracking milestones found for Order #<%= searchedOrderId %>. Shipment is being prepared.
                            </div>
                        <% } %>
                    </div>
                <% } %>
            </div>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

// Refactored commit step: feat(ui): update ordertrack.jsp with graphical shipment timeline
