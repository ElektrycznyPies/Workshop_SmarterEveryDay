<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets list</title>
</head>
<body>
<c:set var="fromwhere" value="user" scope="session" />
<div class="container">
    <h1>Your packets</h1>
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
                    <th>Flsh
                        <a href="#" onclick="alert('Number of flashcards in this packet'); return false;">*</a>
                    </th>
                    <th>Shrd
                        <a href="#" onclick="alert('Dot indicates that other user(s) share this packet with you'); return false;">*</a>
                    </th>
                    <th scope="col">Actions</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${packets}" var="packet" varStatus="status">
                    <tr>
                        <td><c:out value="${packet.name}"/></td>
                        <td><c:out value="${packet.description}"/></td>
                        <td>${packetsWithFlashcards[packet.id]}</td>
                        <td>
                            <c:if test="${packetsShared[packet.id]}">
                                <span style="color: darkred;">•</span>
                            </c:if>
                        </td>
                        <td>
                            <a href="<c:url value='/flashpack/user/packets/edit/${packet.id}'/>" class="button secondary">Edit</a>
                            <a href="<c:url value='/flashpack/user/packets/delete/${packet.id}'/>"
                               onclick="return confirm('${packetsWithFlashcards[packet.id] > 0 ? 'Are you sure? This packet CONTAINS FLASHCARDS!' : 'Are you sure? This packet is empty.'}')"
                               class="button danger">${packetsShared[packet.id] ? 'Drop ' : 'Destroy'}<a href="#" onclick="alert('If packet has more users, it will be deleted only for you. If you are the sole user of this packet, it will be permanently deleted.'); return false;"> *</a></a>
                            <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" class="button info"><br>Show Flashcards</a>
                            <c:if test="${packetsWithFlashcards[packet.id] > 0}">
                                <c:choose>
                                    <c:when test="${!noFieldsSet[packet.id]}">
                                        <a href="<c:url value='/flashpack/user/packets/${packet.id}/sendToBazaar'/>" onclick="confirm('Packet will be sent to Packets Bazaar, where it will be available to other users. Agree?')"><br>To Bazaar</a>
                                        <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>">| Study!</a>
                                    </c:when>
                                    <c:otherwise>
                                        Alert!<a href="#" onclick="alert('There are flashcards in this packet, but no display fields or compare field set up. To study this packet or send it to Packets Bazaar, click on Show Flashcards, then set-up display and compare fields'); return false;">*</a>
                                    </c:otherwise>
                                </c:choose>
                            </c:if>
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