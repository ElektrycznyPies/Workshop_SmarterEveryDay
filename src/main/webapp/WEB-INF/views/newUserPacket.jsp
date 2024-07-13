<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Create New Packet</title>
</head>
<body>
<div class="container">
    <h1>Create New Packet</h1>
    <form:form method="POST" modelAttribute="packet">
        <div>
            <form:label path="name">Packet Name:</form:label>
            <form:input path="name"/>
        </div>
        <div>
            <form:label path="description">Description:</form:label>
            <form:textarea path="description"/>
        </div>
        <div>
            <input type="submit" value="Create Packet"/>
        </div>
    </form:form>
</div>
</body>
</html>
<%--<html>--%>
<%--<head>--%>
<%--    <title>User Packets</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <h1>Packets for ${user.firstName} ${user.lastName}</h1>--%>
<%--    <c:choose>--%>
<%--        <c:when test="${empty packets}">--%>
<%--            <p>This user has no packets.</p>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <section class="grid" id="packets">--%>
<%--                <table class="striped full-width">--%>
<%--                    <thead>--%>
<%--                    <tr>--%>
<%--                        <th scope="col">Packet Name</th>--%>
<%--                        <th scope="col">Description</th>--%>
<%--                        <th scope="col">Action</th>--%>
<%--                    </tr>--%>
<%--                    </thead>--%>
<%--                    <tbody>--%>
<%--                    <c:forEach items="${packets}" var="packet">--%>
<%--                        <tr scope="row">--%>
<%--                            <td><c:out value="${packet.name}"/></td>--%>
<%--                            <td><c:out value="${packet.description}"/></td>--%>
<%--                            <td>--%>
<%--                                <a href="<c:url value="/admin/users/packets/edit/${packet.id}"/>" class="button secondary">Edit</a>--%>
<%--                                <a href="<c:url value="/admin/users/packets/delete/${packet.id}"/>" class="button danger" onclick="return confirm('Are you sure?')">Delete</a>--%>
<%--                            </td>--%>
<%--                        </tr>--%>
<%--                    </c:forEach>--%>
<%--                    </tbody>--%>
<%--                </table>--%>
<%--            </section>--%>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
<%--    <a href="<c:url value="/admin/users/all"/>" class="button">Back to Users List</a>--%>
<%--</div>--%>
<%--</body>--%>
<%--</html>--%>
