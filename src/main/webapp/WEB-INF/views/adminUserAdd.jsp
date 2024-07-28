<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Add user</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">Add user</h2>

        <form:form method="post" modelAttribute="user">

            <form:hidden path="id"/>

        <div class="form-group">
            <label for="firstName">First name</label>
            <form:input path="firstName" class="input"/>
        </div>

        <div class="form-group">
            <label for="lastName">Last name</label>
            <form:input path="lastName" class="input"/>
        </div>
        <div class="form-group">
            <label for="nick">Nick</label>
            <form:input path="nick" class="input"/>
        </div>

        <div class="form-group">
            <label for="email">Email</label>
            <form:input path="email" class="input" required="required"/>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <form:input path="password" class="input" type="password"/>
        </div>

        <div class="form-group">
            <input type="submit" value="Save" class="button primary">
        </div>
        </form:form>

</body>
</html>

