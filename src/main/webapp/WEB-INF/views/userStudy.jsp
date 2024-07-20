<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Study Session</title>
</head>
<body>
<style>
    .res-img {
        width: 600px;
        height: 400px;
        object-fit: cover;
        border: 1px solid #ccc;
        border-radius: 15px;
        padding: 5px;
    }
</style>
<main class="container">
    <c:set var="myContext" value="${pageContext.request.contextPath}"/>
    <div style="text-align: right;">
        <p>Correct answers: ${correctAnswers}</p>
        <p>Wrong answers: ${wrongAnswers}</p>
    </div>
    <h2>Study session for packet: ${packet.name}</h2>
    <div class="flashcard">
        <c:if test="${packet.showFields.contains('name')}">
            <p>Name: ${flashcard.name}</p>
        </c:if>
        <c:if test="${packet.showFields.contains('word')}">
            <p>Word: ${flashcard.word}</p>
        </c:if>
        <c:if test="${packet.showFields.contains('imageLink')}">
            <p><img class="res-img" src="${myContext}/${flashcard.imageLink}" /></p>
        </c:if>
        <c:if test="${packet.showFields.contains('soundLink')}">
            <p><audio controls><source src="${flashcard.soundLink}" type="audio/mpeg"></audio></p>
        </c:if>
        <c:if test="${packet.showFields.contains('additionalText')}">
            <p>Additional Text: ${flashcard.additionalText}</p>
        </c:if>
        <form action="/flashpack/user/packets/${packet.id}/study/answer" method="post">
            <input type="text" name="answer" placeholder="Your answer"/>
            <button type="submit">Submit</button>
        </form>
    </div>
    <form action="/flashpack/user/packets/${packet.id}/study/end" method="post">
        <button type="submit">End session</button>
    </form>
</main>
</body>
</html>
