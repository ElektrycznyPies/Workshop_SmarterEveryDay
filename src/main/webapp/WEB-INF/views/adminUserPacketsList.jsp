<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>User Packets - Admin View</title>
</head>
<body>
<c:set var="fromwhere" value="admin" scope="session" />
<div class="container">
    <a href="<c:url value='/admin/users/all'/>" class="button">Back to users list</a>
    <h1>Packets for ${user.firstName} ${user.lastName} <c:if test="${not empty user.nick}">(${user.nick})</c:if></h1>
    <section id="packets">
        <c:choose>
            <c:when test="${empty packets}">
                <p>This user has no packets.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>FNo.
                            <a href="#" onclick="alert('Number of flashcards in this packet'); return false;">*</a>
                        </th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${packets}" var="packet" varStatus="status">
                        <tr>
                            <td>${packet.name}</td>
                            <td>${packet.description}</td>
                            <td>${packet.author}</td>
                            <td>${packetsWithFlashcards[packet.id]}</td>
                            <td>
                                <a href="<c:url value='/admin/users/packets/${user.id}/delete/${packet.id}'/>"
                                   onclick="return confirm('Are you sure?')"
                                   class="button danger">Destroy<a href="#" onclick="alert('This will permanently delete this packet for all users.'); return false;">*</a></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>
</div>
</body>
</html>




<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%--<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>--%>
<%--<%@ include file="header.jsp" %>--%>

<%--<html>--%>
<%--<head>--%>
<%--    <title>User packets</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<main class="container">--%>
<%--<h1>Packets for ${user.fullName}</h1>--%>
<%--<c:choose>--%>
<%--    <c:when test="${empty packets}">--%>
<%--        <p>This user has no packets.</p>--%>
<%--    </c:when>--%>
<%--    <c:otherwise>--%>
<%--        <ul>--%>

<%--    <c:forEach items="${packets}" var="packet">--%>
<%--        <li>--%>
<%--                ${packet.name} - ${packet.description}--%>
<%--            <a href="/admin/users/packets/${user.id}/delete/${packet.id}" onclick="return confirm('Are you sure?')">Delete</a>--%>
<%--        </li>--%>
<%--    </c:forEach>--%>
<%--        </ul>--%>
<%--    </c:otherwise>--%>
<%--</c:choose>--%>
<%--<a href="/admin/users/all">Back to Users List</a>--%>
<%--</main>--%>
<%--</body>--%>
<%--</html>--%>
