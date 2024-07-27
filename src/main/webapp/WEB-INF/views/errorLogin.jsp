<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
<main class="container">
    <h3>Login error</h3>
    <p>Wrong email or password. Please enter valid credentials or register as a new user. </p>
    <a href="<c:url value='/user/home'/>">Return to login page</a>
</main>
</body>
</html>