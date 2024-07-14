<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Edit User Packet</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/user/home'/>" class="button">Main page</a>
    </div>

    <form:form action="/flashpack/user/packets/edit" method="post" modelAttribute="packet">
        <form:hidden path="id"/>
        <form:hidden path="created_at"/>
        <form:hidden path="updated_at"/>
        <table>
            <tr>
                <td><form:label path="name">Name:</form:label></td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td><form:label path="description">Description:</form:label></td>
                <td><form:input path="description"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit" class="primary">Save</button>
                </td>
            </tr>
        </table>
    </form:form>

</div>
</body>
</html>