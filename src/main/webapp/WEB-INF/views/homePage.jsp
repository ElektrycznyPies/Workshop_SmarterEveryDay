<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>

<html>
<head>
    <title>Smarter Every Day - starting page</title>

</head>
<body>
<main class="container">
    <section id="links">

    <p class="grid">
        <button class="secondary"><a href="<c:url value='/about'/>">About</a></button>
        <button><a href="<c:url value='/login'/>">Login</a></button>
        <button><a href="<c:url value='/register'/>">Register</a></button>
    </p>
    </section>
</main>