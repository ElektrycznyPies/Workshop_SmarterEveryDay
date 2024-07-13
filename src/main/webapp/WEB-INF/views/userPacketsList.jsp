<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>User Packets</title>
</head>
<body>
<div class="container">
    <h1>Your Packets</h1>
    <c:choose>
        <c:when test="${empty packets}">
            <p>You have no packets.</p>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach items="${packets}" var="packet">
                    <li>
                            ${packet.name} - ${packet.description}
                        <a href="/flashpack/user/packets/edit/${packet.id}">Edit</a>
                        <a href="/flashpack/user/packets/delete/${packet.id}" onclick="return confirm('Are you sure?')">Delete</a>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
    <a href="/userPage">Main page</a>
</div>
</body>
</html>