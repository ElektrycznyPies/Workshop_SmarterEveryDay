<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Study Session</title>
</head>
<body>
<main class="container">
    <c:set var="myContext" value="${pageContext.request.contextPath}"/>
    <c:set var="contextPath" value="${pageContext.request.contextPath}" />
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
            <p>1: <img src="${myContext}/${flashcard.imageLink}" /></p>
            <p>2: <img src="webapp/images/${flashcard.imageLink}" /></p>
            <p>3: <img src="images/${flashcard.imageLink}" /></p>
            <p>4: <img src="/images/${flashcard.imageLink}" /></p>
            <p>5: <img src="${pageContext.request.contextPath}/images/${flashcard.imageLink}" alt="Image" /></p>
            <p>6: <img src="${flashcard.imageLink}" alt="Image" /></p>
            <p>ContextPath: <c:out value="${pageContext.request.contextPath}" /></p>
            <p>ContextPath2: <c:out value="${myContext}" /></p>
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
