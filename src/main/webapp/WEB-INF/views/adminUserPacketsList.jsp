<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>User Packets - Admin View</title>
</head>
<body>
<c:set var="fromwhere" value="admin" scope="session" />
<div class="container">
    <a href="<c:url value='/admin/users/all'/>" class="button">Back to users list</a>
    <h1>Packets for ${user.firstName} ${user.lastName} <c:if test="${not empty user.nick}">(${user.nick})</c:if></h1>
    <section id="packets">
        <c:choose>
            <c:when test="${empty packets}">
                <p>This user has no packets.</p>
            </c:when>
            <c:otherwise>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Description</th>
                        <th>Author</th>
                        <th>Flsh
                            <a href="#" onclick="alert('Number of flashcards in this packet'); return false;">*</a>
                        </th>
                        <th>Shrd
                            <a href="#" onclick="alert('Blue dot indicates that other user(s) share this packet as well'); return false;">*</a>
                        </th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${packets}" var="packet" varStatus="status">
                        <tr>
                            <td>${packet.name}</td>
                            <td>${packet.description}</td>
                            <td>${packet.author}</td>
                            <td>${packetsWithFlashcards[packet.id]}</td>
                            <td>
                                <c:if test="${sharedPackets[packet.id]}">
                                    <span style="color: darkblue;">•</span>
                                </c:if>
                            </td>
                            <td>
                                <a href="<c:url value='/admin/users/packets/${user.id}/delete/${packet.id}'/>"
                                   onclick="return confirm('Are you sure?')"
                                   class="button danger">Destroy</a><a href="#" onclick="alert('This will permanently delete this packet for all users.'); return false;"> *</a>
                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" ><br>Show Flashcards</a>
                                <c:if test="${user.id == 0}">
                                <a href="<c:url value='/flashpack/bazaar/get/${packet.id}'/>" ><br>Get packet</a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </section>
</div>
</body>
</html>

