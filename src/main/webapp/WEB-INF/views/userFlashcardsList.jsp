<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>${isEdit ? 'Edit' : 'Add'} Flashcard</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/flashpack/user/packets'/>" class="button">Back to packets list</a>
    </div>

    <h4>Packet name: ${packetName}</h4>
    <c:choose>
        <c:when test="${empty flashcards}">
        <p>No flashcards in this packet</p>
        <form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
            <button type="submit" class="primary">Add Flashcard</button>
        </form>
    </c:when>
    <c:otherwise>
        <p>Select fields to show during study session:</p>
        <form action="<c:url value='/flashpack/user/packets/${packetId}/update-study-settings'/>" method="post">
            <div class="grid container">
                <div><label><input type="checkbox" name="showFields" value="name" ${showFields.contains('name') ? 'checked' : ''}> Name</label></div>
                <div><label><input type="checkbox" name="showFields" value="word" ${showFields.contains('word') ? 'checked' : ''}> Word</label></div>
                <div><label><input type="checkbox" name="showFields" value="additionalText" ${showFields.contains('additionalText') ? 'checked' : ''}> Additional Text</label></div>
                <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink') ? 'checked' : ''}> Image</label></div>
            </div>
        <p>Select the field to compare for correct answer:</p>
        <div class="grid container">
            <div><label><input type="radio" name="compareField" value="name" ${compareField == 'name' ? 'checked' : ''}> Name</label></div>
            <div><label><input type="radio" name="compareField" value="word" ${compareField == 'word' ? 'checked' : ''}> Word</label></div>
            <div><label><input type="radio" name="compareField" value="additionalText" ${compareField == 'additionalText' ? 'checked' : ''}> Additional Text</label></div>
            <div></div>
        </div>
<%--        <div class="grid container">--%>
<%--            <button type="submit" class="primary">Save Study Settings</button>--%>
<%--            <div></div>--%>
<%--            <div></div>--%>
<%--        </div>--%>
<%--        </form>--%>

        <div class="grid container">
            <div><button type="submit" class="primary">Save Study Settings</button></div></form>
            <div><a role="button" href="<c:url value='/flashpack/user/packets/${packetId}/study'/>" class="button primary">Start Study Session</a></div>
            <div><form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
                <button type="submit" class="primary">Add Flashcard</button></form></div>

        </div>

                <section class="grid" id="tables">
                    <table class="striped full-width">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Word</th>
                                <th scope="col">Image Link</th>
                                <th scope="col">Sound Link</th>
                                <th scope="col">Additional Text</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${flashcards}" var="flashcard" varStatus="status">
                                <tr scope="row">
                                    <td><c:out value="${flashcard.name}"/></td>
                                    <td><c:out value="${flashcard.word}"/></td>
                                    <td><c:out value="…${shortImageLinks[status.index]}"/>
                                    </td>
                                    <td><c:out value="${flashcard.soundLink}"/></td>
                                    <td><c:out value="${flashcard.additionalText}"/></td>
                                    <td>
                                        <a href="<c:url value='/flashpack/user/packets/${packetId}/flashcards/edit/${flashcard.id}'/>" class="button secondary">Edit</a>
                                        <a href="<c:url value='/flashpack/user/packets/${packetId}/flashcards/delete/${flashcard.id}'/>" onclick="return confirm('Are you sure?')" class="button danger">Delete</a>
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

