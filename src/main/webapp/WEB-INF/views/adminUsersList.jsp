<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>User List</title>
</head>
<body>
<div class="container">
<%--    <div>--%>
<%--    <a href="<c:url value='/user/home'/>" class="button">Main page</a></div>--%>

    <form action="<c:url value='/admin/users/add'/>" method="get">
        <button type="submit" class="primary">Add User</button>
    </form>

    <section class="grid" id="tables">
        <table class="striped full-width"> <thead>
        <tr>
            <th scope="col">First Name</th>
            <th scope="col">Last Name</th>
            <th scope="col">Nick</th>
            <th scope="col">Email</th>
            <th scope="col">Action</th>
            <th scope="col">HP
                <a href="#" onclick="alert('User with blue dot Has Packet(s) that are assigned exclusively to them'); return false;"><small>*</small></a>
            </th>

        </tr>
        </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr scope="row">
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.nick}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td>
                        <c:choose>
                            <c:when test="${user.id != 0}">
                                <a href="<c:url value='/admin/users/edit/${user.id}'/>" class="button secondary">Edit</a>
                                <a href="<c:url value='/admin/users/delete/${user.id}'/>"
                                   onclick="return confirm('${usersWithPackets[user.id] ? 'Are you sure? This user HAS EXCLUSIVE PACKETS! After deletion these packets will be assigned to the Default User (id = 0)' : 'Are you sure? User has no exclusive packets.'}')"
                                        class="button danger">Delete</a>
                            </c:when>
                        </c:choose>
                        <a href="<c:url value='/admin/users/packets/${user.id}'/>" class="button info">Packets</a>
                    <td><c:if test="${usersWithPackets[user.id] == true}"><span style="color: blue;">•</span></c:if></td>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>
</body>
</html>
