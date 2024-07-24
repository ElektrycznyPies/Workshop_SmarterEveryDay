<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets Bazaar</title>
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
        <p>Select categories of the packets you're interested in
<%--            <a href="#" onclick="alert('These elements will be presented to you during the study session (eg.: if you want to learn birds\' names by their photos, select Image here, but not Name)'); return false;">*</a>--%>
        </p>
        <form method="get" action="/flashpack/bazaar">
            <c:forEach var="category" items="${categories}">
            <input type="checkbox" name="category" value="${category.id}">${category.name}</input>
            </c:forEach>
            <button type="submit">Filter</button>
        </form>

<%--        <form action="<c:url value='/flashpack/user/packets/${packetId}/update-study-settings'/>" method="post">--%>
<%--            <div class="grid container">--%>
<%--                <div><label><input type="checkbox" name="showFields" value="name" ${showFields.contains('name') ? 'checked' : ''}> Name</label></div>--%>
<%--                <div><label><input type="checkbox" name="showFields" value="word" ${showFields.contains('word') ? 'checked' : ''}> Word 1</label></div>--%>
<%--                <div><label><input type="checkbox" name="showFields" value="word2" ${showFields.contains('word2') ? 'checked' : ''}> Word 2</label></div>--%>
<%--                <div><label><input type="checkbox" name="showFields" value="additionalText" ${showFields.contains('additionalText') ? 'checked' : ''}> Additional text</label></div>--%>
<%--                <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink') ? 'checked' : ''}> Image</label></div>--%>
<%--                    &lt;%&ndash;                <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink2') ? 'checked' : ''}> Image 2</label></div>&ndash;%&gt;--%>
<%--            </div>--%>

            <section class="grid" id="tables">
                <table class="striped full-width">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Author</th>
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
                            <td><c:out value="${packet.author}"/></td>
                            <td>
<%--                                <a href="<c:url value='/flashpack/user/packets/edit/${packet.id}'/>" class="button secondary">Edit</a>--%>
<%--                                <a href="<c:url value='/flashpack/user/packets/delete/${packet.id}'/>"--%>
<%--                                   onclick="return confirm('${packetsWithFlashcards[packet.id] > 0 ? 'Are you sure? This packet CONTAINS FLASHCARDS!' : 'Are you sure? This packet is empty.'}')"--%>
<%--                                   class="button danger">Delete</a>--%>
                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" class="button info">Show Flashcards</a>
                                <a href="<c:url value='/flashpack/bazaar/get/${packet.id}'/>" class="button info">Get packet</a>
<%--                                <c:if test="${packetsWithFlashcards[packet.id] > 0}">--%>
<%--                                    <c:choose>--%>
<%--                                        <c:when test="${!noFieldsSet[packet.id]}">--%>
<%--                                            <a href="<c:url value='/flashpack/user/packets/sendToBazaar/${packet.id}'/>"> To Bazaar</a>--%>
<%--                                            <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>"> Study!</a>--%>
<%--                                        </c:when>--%>
<%--                                        <c:otherwise>--%>
<%--                                            Alert<a href="#" onclick="alert('There are flashcards in this packet, but no display fields or compare field set up. To study this packet, click on Show Flashcards, then set-up display and compare fields'); return false;">*</a>--%>
<%--                                        </c:otherwise>--%>
<%--                                    </c:choose>--%>
<%--                                    &lt;%&ndash;                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/study'/>"> Study!</a>&ndash;%&gt;--%>
<%--                                </c:if>--%>
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