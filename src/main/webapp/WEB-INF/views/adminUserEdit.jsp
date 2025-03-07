<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Edit user</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">Edit user</h2>

        <c:url var="edit_url" value="/admin/users/edit"/>
        <form:form method="post" modelAttribute="user" action="${edit_url}">

    <form:hidden path="id"/>
    <form:hidden path="created_at"/>
    <form:hidden path="updated_at"/>
    <form:hidden path="password"/>

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
        <label for="lastName">Nick</label>
        <form:input path="nick" class="input"/>
        <form:errors path="nick" class="text-danger" />
    </div>

    <div class="form-group">
        <label for="email">Email</label>
        <form:input path="email" class="input" required="required"/>
        <form:errors path="email" class="text-danger" />
    </div>
    <div>
        <label for="isAdmin">Admin:</label>
        <input type="checkbox" id="isAdmin" name="isAdmin" ${user.role == 1 ? 'checked' : ''}>
    </div>
    <div class="form-group">
        <input type="submit" value="Save" class="button primary">
    </div>
</form:form>

