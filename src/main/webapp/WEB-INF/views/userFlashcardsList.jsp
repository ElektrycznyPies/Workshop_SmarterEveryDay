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

    <h3>Packet name: ${packetName}</h3>
    <c:choose>
    <c:when test="${empty flashcards}">
    <p>No flashcards</p>
    <form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
        <button type="submit" class="primary">Add Flashcard</button>
    </form>
    </c:when>
    <c:otherwise>
    <div class="container">
        <form action="<c:url value='/flashpack/user/packets/${packetId}/update-study-settings'/>" method="post">            <h4>Select fields to show during study:</h4>
            <label><input type="checkbox" name="showFields" value="image" ${showFields.contains('image') ? 'checked' : ''}> Image</label>
            <label><input type="checkbox" name="showFields" value="name" ${showFields.contains('name') ? 'checked' : ''}> Name</label>
            <label><input type="checkbox" name="showFields" value="word" ${showFields.contains('word') ? 'checked' : ''}> Word</label>
            <label><input type="checkbox" name="showFields" value="additionalText" ${showFields.contains('additionalText') ? 'checked' : ''}> Additional Text</label>

            <label><input type="radio" name="compareField" value="name" ${compareField == 'name' ? 'checked' : ''}> Name</label>
            <label><input type="radio" name="compareField" value="word" ${compareField == 'word' ? 'checked' : ''}> Word</label>
            <label><input type="radio" name="compareField" value="additionalText" ${compareField == 'additionalText' ? 'checked' : ''}> Additional Text</label>

            <button type="submit" class="primary">Save Study Settings</button>
            <a href="<c:url value='/flashpack/user/packets/${packetId}/study'/>" class="button primary">Start Study Session</a>
        </form>
        <form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
        <button type="submit" class="primary">Add Flashcard</button>
    </form>

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
                            <c:forEach items="${flashcards}" var="flashcard">
                                <tr scope="row">
                                    <td><c:out value="${flashcard.name}"/></td>
                                    <td><c:out value="${flashcard.word}"/></td>
                                    <td><c:out value="${flashcard.imageLink}"/></td>
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

