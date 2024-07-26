<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Packets Bazaar</title>
</head>
<body>
<main class="container">
    <h3>Packets Bazaar</h3>
    <c:set var="fromwhere" value="bazaar" scope="session" />
<div >
    <c:choose>
        <c:when test="${empty packets and empty packets_grey}">
            <section class="grid" id="tables">
                <p>No packets</p>
            </section>
        </c:when>
        <c:otherwise>
            <p>Select categories of the packets you're interested in

            </p>
            <form method="get" action="/flashpack/bazaar">
                <c:forEach var="category" items="${categories}">
                    <input type="checkbox" name="category" value="${category.id}">${category.name}</input>
                </c:forEach>
                <button type="submit" disabled>Filter</button>
            </form>

            <%-- <form action="<c:url value='/flashpack/user/packets/${packetId}/update-study-settings'/>" method="post"> --%>
            <%-- <div class="grid container"> --%>
            <%-- <div><label><input type="checkbox" name="showFields" value="name" ${showFields.contains('name') ? 'checked' : ''}> Name</label></div> --%>
            <%-- <div><label><input type="checkbox" name="showFields" value="word" ${showFields.contains('word') ? 'checked' : ''}> Word 1</label></div> --%>
            <%-- <div><label><input type="checkbox" name="showFields" value="word2" ${showFields.contains('word2') ? 'checked' : ''}> Word 2</label></div> --%>
            <%-- <div><label><input type="checkbox" name="showFields" value="additionalText" ${showFields.contains('additionalText') ? 'checked' : ''}> Additional text</label></div> --%>
            <%-- <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink') ? 'checked' : ''}> Image</label></div> --%>
            <%-- </div> --%>
            <%-- </form> --%>
            <section class="grid" id="tables">
                <table class="striped full-width">
                    <thead>
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Description</th>
                        <th scope="col">Author</th>
                        <th scope="col">Actions</th>
                        <th>Flsh
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
                                <a href="<c:url value='/flashpack/user/packets/${packet.id}/flashcards'/>" onclick=""> Show Flashcards</a><br>
                                <a href="<c:url value='/flashpack/bazaar/get/${packet.id}'/>" class="button info">Get packet</a>
                            </td>
                            <td>${packetsWithFlashcardsBaz[packet.id]}</td>
                        </tr>
                    </c:forEach>
                    <c:if test="${not empty packets_grey}">
                        <tr><td colspan="5"><i>Packets you already have</i></td></tr>
                        <c:forEach items="${packets_grey}" var="packet_grey" varStatus="status">
                            <tr style="color: grey;">
                                <td style="color: grey;"><c:out value="${packet_grey.name}"/></td>
                                <td style="color: grey;"><c:out value="${packet_grey.description}"/></td>
                                <td style="color: grey;"><c:out value="${packet_grey.author}"/></td>
                                <td style="color: grey;"></td>
                                <td style="color: grey;">${greyPacketsWithFlashcardsBaz[packet_grey.id]}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
            </section>

        </c:otherwise>
    </c:choose>
</div>
</main>
</body>
</html>
