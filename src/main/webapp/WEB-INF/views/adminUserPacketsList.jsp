<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>User Packets</title>
</head>
<body>
<h1>Packets for ${user.firstName} ${user.lastName}</h1>
<c:choose>
    <c:when test="${empty packets}">
        <p>This user has no packets.</p>
    </c:when>
    <c:otherwise>
        <ul>
            <c:forEach items="${packets}" var="package">
                <li>
                        ${packet.name} - ${packet.description}
                    <a href="/admin/packages/edit/${packet.id}">Edit</a>
                    <a href="/admin/packages/delete/${packet.id}" onclick="return confirm('Are you sure?')">Delete</a>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>
<a href="/admin/users/all">Back to Users List</a>
</body>
</html>


<%--<html>--%>
<%--<head>--%>
<%--    <title>User List</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>

<%--    <section class="grid" id="tables">--%>
<%--        <table class="striped full-width"> <thead>--%>
<%--        <tr>--%>
<%--            <th scope="col">Packet Name</th>--%>
<%--            <th scope="col">Last Name</th>--%>
<%--            <th scope="col">Email</th>--%>
<%--            <th scope="col">Action</th>--%>
<%--        </tr>--%>
<%--        </thead>--%>
<%--            <tbody>--%>
<%--            <tr scope="row">--%>
<%--                <td>User: </td>--%>
<%--                <td><c:out value="${user.firstName}"/></td>--%>
<%--                <td><c:out value="${user.lastName}"/></td>--%>
<%--                <td><c:out value="${user.email}"/></td>--%>
<%--            </tr>--%>
<%--            <c:forEach items="${users}" var="user">--%>
<%--                <tr scope="row">--%>
<%--                    <td><c:out value="${user.firstName}"/></td>--%>
<%--                    <td><c:out value="${user.lastName}"/></td>--%>
<%--                    <td><c:out value="${user.email}"/></td>--%>
<%--                    <td>--%>
<%--                        <a href="<c:url value="/admin/users/edit/${user.id}"/>" class="button secondary">Edit</a>--%>
<%--                        <a href="<c:url value="/admin/users/delete/${user.id}"/>" class="button danger">Delete</a>--%>
<%--                        <a href="<c:url value="/admin/users/packages/${user.id}"/>" class="button info">Packages</a>--%>
<%--                    </td>--%>
<%--                </tr>--%>
<%--            </c:forEach>--%>
<%--            </tbody>--%>
<%--        </table>--%>
<%--    </section>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
