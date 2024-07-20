<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - error page</title>

</head>
<body>
<main class="container">
    <h3>Oh, no!</h3>
    <p>An error occured...</p>
    <p>${errorMessage}</p>
    <p>URL: ${url}</p>
    <hr>
    <p>Details: ${exception}</p>
    <a href="<c:url value='/'/>">Return to home page</a>


</main>
</body>
</html>