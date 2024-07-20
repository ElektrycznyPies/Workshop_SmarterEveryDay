<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets List</title>
</head>
<body>
<div class="container">
<%--    <div>--%>
<%--        <a href="<c:url value='/user/home'/>" class="button">Main page</a>--%>
<%--    </div>--%>
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
                    <th scope="col">HF
                        <a href="#" onclick="alert('Packet with blue dot Has Flashcard(s)'); return false;">*</a>
                    </th>
                    <th>No.</th>
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
                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>"> Study!</a>
                            </c:if>
                        </td>
                        <td>
                            <c:if test="${packetsWithFlashcards[packet.id] > 0}"><span style="color: blue;">•</span></c:if>
                        </td>
                        <td>
                        ${packetsWithFlashcards[packet.id]}
                        </td>
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