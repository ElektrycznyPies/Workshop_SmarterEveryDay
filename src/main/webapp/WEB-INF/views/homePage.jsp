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
            <p>
                <%@ include file="loginUserComponent.jsp" %>
            </p>
        </details>
    </section>
    <section id="register">
            <details>
                <summary>Register</summary>
                <p>
                    <c:set var="user" value="${user}" scope="request"/>
                    <%@ include file="registerUserComponent.jsp" %>
                </p>
            </details>
        </section>
        <section id="about">
            <div>
                <a href="<c:url value='/about'/>">About</a>
            </div>

<%--        <p class="grid">--%>
<%--        <button class="secondary"><a href="<c:url value='/about'/>" class="secondary">About</a></button>--%>
<%--        <button><a href="<c:url value='/login'/>">Login</a></button>--%>
<%--        <button><a href="<c:url value='/register'/>">Register</a></button>--%>
<%--    </p>--%>
    </section>
</main>