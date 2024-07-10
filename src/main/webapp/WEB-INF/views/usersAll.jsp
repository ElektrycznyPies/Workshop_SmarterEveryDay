<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="headerAdmin.jsp" %>

<html>
<head>
    <title>User List</title>
</head>
<body>
<div class="container">
    <section class="grid text-center"> <a href="<c:url value="/admin/users/add"/>" class="button primary">Add User</a>
    </section>
    <section class="grid">
        <table class="table full-width"> <thead>
        <tr>
            <th>First Name</th>
            <th>Last Name</th>
            <th>Email</th>
            <th>Action</th>
        </tr>
        </thead>
            <tbody>
            <c:forEach items="${users}" var="user">
                <tr>
                    <td><c:out value="${user.firstName}"/></td>
                    <td><c:out value="${user.lastName}"/></td>
                    <td><c:out value="${user.email}"/></td>
                    <td>
                        <a href="<c:url value="/admin/users/edit/${user.id}"/>" class="button secondary">Edit</a>
                        <a href="<c:url value="/admin/users/delete/${user.id}"/>" class="button danger">Delete</a>
                        <a href="<c:url value="/admin/users/show/${user.id}"/>" class="button info">Details</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </section>
</div>
</body>
</html>
