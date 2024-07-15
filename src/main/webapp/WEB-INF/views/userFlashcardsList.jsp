<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Flashcards List</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/user/home'/>" class="button">Main page</a>
    </div>

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
</div>
</body>
</html>