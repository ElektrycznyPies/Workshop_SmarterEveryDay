<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>

<html>
<head>
    <title>Edit User</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">Edit User</h2>

        <c:url var="edit_url" value="/admin/users/edit"/>
        <form:form method="post" modelAttribute="user" action="${edit_url}">

    <form:hidden path="id"/>
    <form:hidden path="created_at"/>
    <form:hidden path="updated_at"/>
    <form:hidden path="hashed_password"/>

    <div class="form-group">
        <label for="firstName">First Name</label>
        <form:input path="firstName" class="input"/>
        <form:errors path="firstName" class="text-danger" />
    </div>

    <div class="form-group">
        <label for="lastName">Last Name</label>
        <form:input path="lastName" class="input"/>
        <form:errors path="lastName" class="text-danger" />
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <form:input path="email" class="input"/>
        <form:errors path="email" class="text-danger" />
    </div>

    <div class="form-group">
        <input type="submit" value="Save" class="button primary">
    </div>
</form:form>

