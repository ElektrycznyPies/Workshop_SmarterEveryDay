<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - user/admin page</title>

</head>
<body>
<main class="container">

    <section id="stats">
        <details>
            <summary><p role="button">Your statistics</p></summary>
            <div class="content">
                <c:choose>
                    <c:when test="${not empty sortedPackets}">
                        <table>
                            <thead>
                            <tr>
                                <th rowspan="2">10 most studied packet(s)</th>
                                <th rowspan="2">HH:mm:ss</th>
                                <th colspan="3" style="color: green">Avg correct answers</th>
                                <th colspan="3" style="color: darkred">Avg wrong answers</th>
                            </tr>
                            <tr>
                                <th>Total</th>
                                <th>Last 3 sess.</th>
                                <th>Last sess.</th>
                                <th>Total</th>
                                <th>Last 3 sess.</th>
                                <th>Last sess.</th>
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
                                    <td>
                                        <c:set var="correctPercentageTotal" value="${correctAnswerPercentagesTotal[packet]}" />
                                        <c:out value="${correctPercentageTotal != null ? correctPercentageTotal : 0}" />%
                                    </td>
                                    <td>
                                        <c:set var="correctPercentageRecent" value="${correctAnswerPercentagesRecent[packet]}" />
                                        <c:out value="${correctPercentageRecent != null ? correctPercentageRecent : 0}" />%
                                    </td>
                                    <td>
                                        <c:set var="correctPercentageLastOne" value="${correctAnswerPercentagesLastOne[packet]}" />
                                        <c:out value="${correctPercentageLastOne != null ? correctPercentageLastOne : 0}" />%
                                    </td>
                                    <td>
                                        <c:set var="wrongPercentageTotal" value="${wrongAnswerPercentagesTotal[packet]}" />
                                        <c:out value="${wrongPercentageTotal != null ? wrongPercentageTotal : 0}" />%
                                    </td>
                                    <td>
                                        <c:set var="wrongPercentageRecent" value="${wrongAnswerPercentagesRecent[packet]}" />
                                        <c:out value="${wrongPercentageRecent != null ? wrongPercentageRecent : 0}" />%
                                    </td>
                                    <td>
                                        <c:set var="wrongPercentageLastOne" value="${wrongAnswerPercentagesLastOne[packet]}" />
                                        <c:out value="${wrongPercentageLastOne != null ? wrongPercentageLastOne : 0}" />%
                                    </td>
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
                                <td colspan="6"></td>
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

    <div class="grid">
<%--    <section id="packages">--%>
            <div>
                <a href="<c:url value='/flashpack/user/packets'/>"><button>Available packets</button></a>
            </div>
<%--    </section>--%>
<%--    <section id="new_package">--%>
            <div>
                <a href="<c:url value='/flashpack/new/packet'/>"><button>Create new packet</button></a>
            </div>
<%--    </section>--%>
<%--    <section id="profile">--%>
        <div>
            <a href="<c:url value='/user/edit'/>"><button>Edit your profile</button></a>
        </div>
<%--    </section>--%>
        <div>

        <c:choose>
        <c:when test="${not empty lastSessionName}">
            <a href="<c:url value='/flashpack/user/packets/${lastSessionPacketId}/study'/>"><p role="button">Study last<br><small>(${lastSessionName})</small></p></a>
        </c:when>
        <c:otherwise>
            <p role="button" disabled>No last session<br><small>to study</small></p>
        </c:otherwise>
        </c:choose>
        </div>
    </div>
    <c:choose>
        <c:when test="${sessionScope.user.role == 1}">
        <section id="admin"><br><br>
            <hr>
            <p>Admin section</p>
                <div class="grid">
                    <div>
                        <a href="<c:url value='/admin/users/all'/>"><p role="button">List and manage users</p></a>
                    </div>
                </div>
        </section>
        </c:when>
    </c:choose>
</main>
</body>