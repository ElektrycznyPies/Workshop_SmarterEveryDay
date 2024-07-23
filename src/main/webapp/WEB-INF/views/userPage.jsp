<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
                <c:choose>
                    <c:when test="${not empty sortedPackets}">
                <table>
                    <thead>
                    <tr>
                        <th>Most studied packet(s)</th>
                        <th>HH:MM:SS</th>
                        <th>Avg correct answers</th>
                        <th>Avg wrong answers</th>
                    </tr>
                    </thead>
                    <tbody>

                    <c:forEach var="packet" items="${sortedPackets}">
                        <tr>
                            <td>${packet.name}</td>
                            <td>
                                <c:set var="duration" value="${durationMap[packet]}" />
                                <c:set var="hours" value="${duration / 3600}" />
                                <c:set var="minutes" value="${(duration % 3600) / 60}" />
                                <c:set var="seconds" value="${duration % 60}" />
                                <fmt:formatNumber value="${hours}" pattern="00"/>:<fmt:formatNumber value="${minutes}" pattern="00"/>:<fmt:formatNumber value="${seconds}" pattern="00"/>
                            </td>
                            <td><!-- Wstaw tutaj logikę do obliczenia średnich poprawnych odpowiedzi --></td>
                            <td><!-- Wstaw tutaj logikę do obliczenia średnich błędów --></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                    <tfoot>
                    <tr>
                        <td>Total study time:</td>
                        <td>
                            <c:set var="totalHours" value="${totalDuration / 3600}" />
                            <c:set var="totalMinutes" value="${(totalDuration % 3600) / 60}" />
                            <c:set var="totalSeconds" value="${totalDuration % 60}" />
                            <fmt:formatNumber value="${totalHours}" pattern="00"/>:<fmt:formatNumber value="${totalMinutes}" pattern="00"/>:<fmt:formatNumber value="${totalSeconds}" pattern="00"/>
                        </td>
                        <td></td>
                        <td></td>
                    </tr>
                    </tfoot>
                </table>
                    </c:when>
                    <c:otherwise>
                        <p>No study sessions to analyze</p>
                    </c:otherwise>
                </c:choose>
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