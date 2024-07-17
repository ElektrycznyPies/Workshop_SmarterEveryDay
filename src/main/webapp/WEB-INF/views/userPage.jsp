<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - user/admin page</title>

</head>
<body>
<main class="container">
    </section>
    <section id="profile">
        <div class="content">
            <a href="<c:url value='/user/edit'/>" class="button">Edit your profile</a>
        </div>
    </section>
    <section id="stats">
        <details>
            <summary>Your statistics</summary>
            <div class="content">
                <%@ include file="statsUserComponent.jsp" %>
                <%--            całkowity czas spędzony na nauce--%>
                <%--            ulubiony pakiet -- czas spędzony w nim--%>
                <%--            ranga na pakiet--%>
            </div>
        </details>
    </section>
    <section id="packages">
            <div class="content">
                <a href="<c:url value='/flashpack/user/packets'/>" class="button">Available packets</a>
            </div>
    </section>
    <section id="new_package">
            <div class="content">
                <a href="<c:url value='/flashpack/new/packet'/>">Create new packet</a>
            </div>
    </section>
    <c:choose>
        <c:when test="${sessionScope.user.role == 1}">
        <section id="admin">
            <hr>
            <p>Admin section</p>
                <div class="grid">
                    <div>
                        <a href="<c:url value='/admin/users/all'/>" class="button">List and manage users</a>
                    </div>
                </div>
        </section>
        </c:when>
    </c:choose>
</main>
</body>