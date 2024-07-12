<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - user page</title>

</head>
<body>
<main class="container">
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
        <details>
            <summary>Available packages</summary>
            <div class="content">
                <%@ include file="showUserPackages.jsp" %>
            </div>
        </details>
    </section>
    <section id="new_package">
            <details>
                <div class="content">
                    <a href="<c:url value='/newPackage'/>" class="button">Create new package</a>
                </div>
            </details>
    </section>
    <c:choose>
        <c:when test="${sessionScope.user.role == 1}">
        <section id="admin">
            <hr>
            <p>Admin section</p>
                <div class="grid">
                    <div>
                        List and manage users
                    </div>
                    <div>
                        Add user
                    </div>
                    <div>
                        Coś tam TRZECIEGO do dodania
                    </div>
        </section>
        </c:when>
    </c:choose>
</main>
</body>