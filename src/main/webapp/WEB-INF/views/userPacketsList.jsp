<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets List</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/user/home'/>" class="button">Main page</a>
    </div>
    <c:choose>
    <c:when test="${empty packets}">
    <section class="grid" id="tables">
    <p>No packets</p>
    </section>
    </c:when>
    <c:otherwise>

    <section class="grid" id="tables">
        <table class="striped full-width">
            <thead>
            <tr>
                <th scope="col">Name</th>
                <th scope="col">Description</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${packets}" var="packet">
                <tr scope="row">
                    <td><c:out value="${packet.name}"/></td>
                    <td><c:out value="${packet.description}"/></td>
                    <td>
                        <a href="<c:url value='/flashpack/user/packets/edit/${packet.id}'/>" class="button secondary">Edit</a>
                        <a href="<c:url value='/flashpack/user/packets/delete/${packet.id}'/>" onclick="return confirm('Are you sure?')" class="button danger">Delete</a>
                        <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" class="button info">Show Flashcards</a>
                        <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>" class="button info">Study</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>