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
        <a href="<c:url value='/flashpack/user/packets/${packetId}/flashcards'/>" class="button">Back to flashcards list</a>
    </div>

    <h2>${isEdit ? 'Edit' : 'Add'} Flashcard</h2>
    <h3>Packet name: ${packetName}</h3>

    <form:form action="${isEdit ? '/flashpack/user/packets/'.concat(packetId).concat('/flashcards/edit') : '/flashpack/user/packets/'.concat(packetId).concat('/flashcards/add')}" method="post" modelAttribute="flashcard" enctype="multipart/form-data">
        <form:hidden path="id" />

        <label for="name">Name</label>
        <form:input path="name" id="name" required="true" />

        <label for="word">Word 1</label>
        <form:input path="word" id="word" />

        <label for="word2">Word 2</label>
        <form:input path="word2" id="word2" />

        <fieldset role="group">
            <label for="fileInput">Choose image</label>
            <input type="file" id="fileInput" name="file">
        </fieldset>
        <fieldset role="group">
            <form:input path="imageLink" name="imageLink" id="imageLink" placeholder="Image" readonly="true"/>
        </fieldset>

        <label for="additionalText">Additional text</label>
        <form:textarea path="additionalText" id="additionalText" rows="4"/>

        <button type="submit" class="primary">${isEdit ? 'Update' : 'Add'} Flashcard</button>

    </form:form>
</div>

</body>
</html>