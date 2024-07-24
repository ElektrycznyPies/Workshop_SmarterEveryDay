<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets list</title>
</head>
<body>
<div class="container">
    <c:choose>
    <c:when test="${empty packets}">
    <section class="grid" id="tables">
    <p>No packets</p>
    </section>
    </c:when>
    <c:otherwise>
        <section class="grid" id="tables">
            <table class="striped full-width">
                <thead>
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Description</th>
                    <th scope="col">Actions</th>
                    <th>FNo.
                        <a href="#" onclick="alert('Number of flashcards in this packet'); return false;">*</a>
                    </th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${packets}" var="packet" varStatus="status">
                    <tr>
                        <td><c:out value="${packet.name}"/></td>
                        <td><c:out value="${packet.description}"/></td>
                        <td>
                            <a href="<c:url value='/flashpack/user/packets/edit/${packet.id}'/>" class="button secondary">Edit</a>
                            <a href="<c:url value='/flashpack/user/packets/delete/${packet.id}'/>"
                               onclick="return confirm('${packetsWithFlashcards[packet.id] > 0 ? 'Are you sure? This packet CONTAINS FLASHCARDS!' : 'Are you sure? This packet is empty.'}')"
                               class="button danger">Delete</a>
                            <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" class="button info">Show Flashcards</a>
                            <c:if test="${packetsWithFlashcards[packet.id] > 0}">
                                <c:choose>
                                    <c:when test="${!noFieldsSet[packet.id]}">
<%--                                        <form action="/flashpack/user/packets/sendToBazaar/${packet.id}" method="post" style="display: inline;">--%>
<%--                                            <input type="hidden">--%>
<%--                                            <button type="submit" style="background: none; border: none; color: blue; text-decoration: underline; cursor: pointer;">To Bazaar</button>--%>
<%--                                        </form>--%>
                                        <a href="<c:url value='/flashpack/user/packets/sendToBazaar/${packet.id}'/>"> To Bazaar</a>
                                        <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>"> Study!</a>
                                    </c:when>
                                    <c:otherwise>
                                        Alert<a href="#" onclick="alert('There are flashcards in this packet, but no display fields or compare field set up. To study this packet, click on Show Flashcards, then set-up display and compare fields'); return false;">*</a>
                                    </c:otherwise>
                                </c:choose>
<%--                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>"> Study!</a>--%>
                            </c:if>
                        </td>
                        <td>${packetsWithFlashcards[packet.id]}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </section>
    </c:otherwise>
    </c:choose>
</div>
</body>
</html>