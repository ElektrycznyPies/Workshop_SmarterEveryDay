<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="headerStudy.jsp" %>
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
        .header {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        .stats {
            display: flex;
            flex-direction: column;
            text-align: right;
        }

    }
</style>

<main class="container">
    <c:set var="myContext" value="${pageContext.request.contextPath}"/>
    <div>
    <div class="header">
        <h2>Study session for packet: ${packet.name}</h2></div>
        <div class="stats"><p><span style="color: green"> Correct answers:</span> ${correctAnswers} | <span style="color: darkred">Wrong answers:</span> ${wrongAnswers}</p>
        </div>
    </div>

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
        <form id="answerForm" action="/flashpack/user/packets/${packet.id}/study/answer" method="post">
<%--        <form action="/flashpack/user/packets/${packet.id}/study/answer" method="post">--%>
            <input type="text" name="answer" placeholder="Your answer" autofocus />
            <button type="submit">Submit</button>
        </form>
    </div>
<%--    <form action="/flashpack/user/packets/${packet.id}/study/end" method="post">--%>
        <form action="<c:url value='/flashpack/user/packets/${packet.id}/study/end'/>" method="post">
            <button type="submit">End session</button>
        </form>

</main>
<c:if test="${not empty correctAnswer}">
    <script>
        alert("Correct answer: ${correctAnswer}");
    </script>
</c:if>
</body>
</html>
