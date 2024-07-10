<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>

<html>
<head>
    <title>Add User</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">Add User</h2>

        <form:form method="post" modelAttribute="user">

            <form:hidden path="id"/>

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
            <label for="hashed_password">Password</label>
            <form:input path="hashed_password" class="input"/>
            <form:errors path="hashed_password" class="text-danger" />
        </div>

        <div class="form-group">
            <input type="submit" value="Save" class="button primary">
        </div>
        </form:form>





<%--        <html>--%>
<%--<head>--%>
<%--    <title>Add User</title>--%>
<%--</head>--%>
<%--<body>--%>

<%--<form:form method="post" modelAttribute="user">--%>
<%--    <p>First Name</p>--%>
<%--    <form:input path="firstName"/>--%>
<%--    <form:errors path="firstName"/><br/>--%>
<%--    <p>Last Name</p>--%>
<%--    <form:input path="lastName"/>--%>
<%--    <form:errors path="lastName"/><br/>--%>
<%--    <p>Email</p>--%>
<%--    <form:input path="email"/>--%>
<%--    <form:errors path="email"/><br/>--%>
<%--    <p>Password</p>--%>
<%--    <form:input path="hashed_password"/>--%>
<%--    <form:errors path="hashed_password"/><br/>--%>

<%--    <input type="submit" value="Save">--%>
<%--</form:form>--%>

</body>
</html>

