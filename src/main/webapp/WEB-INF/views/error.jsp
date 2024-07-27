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
<h3>Oh, no! An error occurred...</h3>
<p>${errorMessage}</p>
<p>Class: ${className}</p>
<p>Method: ${methodName}</p>
<p>Line: ${lineNumber}</p>
    <p>Details: ${exception}</p>
    <p>URL: ${url}</p>
    <hr>
    <a href="<c:url value='/user/home'/>">Return to home page</a>


</main>
</body>
</html>