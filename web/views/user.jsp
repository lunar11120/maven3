<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.example.model.User" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>User Profile</title>
</head>
<body>
    <h1>User Profile</h1>
    <% User user = (User) session.getAttribute("user"); %>
    <p>Name: <%= user.getName() %></p>
    <p>Email: <%= user.getEmail() %></p>
</body>
</html>