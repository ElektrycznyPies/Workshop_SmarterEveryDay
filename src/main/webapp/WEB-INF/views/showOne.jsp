<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>

<html>
<head>
    <title>User Details</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">User Details</h2>

        <div class="form-grid">
            <div class="form-group">
                <label for="firstName">First Name</label>
                <c:out value="${user.firstName}"/>
            </div>

            <div class="form-group">
                <label for="lastName">Last Name</label>
                <c:out value="${user.lastName}"/>
            </div>

            <div class="form-group">
                <label for="email">Email</label>
                <c:out value="${user.email}"/>
            </div>
        </div>
