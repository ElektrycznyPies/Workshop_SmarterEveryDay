<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Registration</title>
</head>
<body>

<main class="container">
    <section class="grid">
        <h2 class="text-center">User Registration</h2>

        <form:form method="post" modelAttribute="user" class="form">

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
                <label for="password">Password</label>
                <form:input path="password" class="input" type="password"/>
                <form:errors path="password" class="text-danger" />
            </div>

            <div class="form-group">
                <input type="submit" value="Register" class="button primary">
            </div>
        </form:form>
    </section>
</main>

</body>
</html>
