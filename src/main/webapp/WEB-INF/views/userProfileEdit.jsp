<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Edit Profile</title>
</head>
<body>
<main class="container">
<h2>Edit your profile</h2>
<form:form method="post" modelAttribute="user">
    <form:hidden path="id"/>
    <div>
        <form:label path="firstName">First name</form:label>
        <form:input path="firstName"/>
        <form:errors path="firstName"/>
    </div>
    <div>
        <form:label path="lastName">Last name</form:label>
        <form:input path="lastName"/>
        <form:errors path="lastName"/>
    </div>
    <div>
        <form:label path="nick">Nick</form:label>
        <form:input path="nick"/>
        <form:errors path="nick"/>
    </div>
    <div>
        <form:label path="email">Email</form:label>
        <form:input path="email"/>
        <form:errors path="email"/>
    </div>
    <div>
        <form:label path="password">New password (leave blank to keep current)</form:label>
        <form:password path="password"/>
        <form:errors path="password"/>
    </div>
    <div>
        <input type="submit" value="Update Profile">
    </div>
</form:form>
</main>
</body>
</html>