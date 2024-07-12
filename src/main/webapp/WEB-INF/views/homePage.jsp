<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - starting page</title>

</head>
<body>
<main class="container">
    <section id="login">
        <details>
            <summary>Login</summary>
            <div class="content">
                <%@ include file="loginUserComponent.jsp" %>
            </div>
        </details>
    </section>
    <section id="register">
            <details>
                <summary>Register</summary>
                <div class="content">
                    <c:set var="user" value="${user}" scope="request"/>
                    <%@ include file="registerUserComponent.jsp" %>
                </div>
            </details>
        </section>
</main>
</body>