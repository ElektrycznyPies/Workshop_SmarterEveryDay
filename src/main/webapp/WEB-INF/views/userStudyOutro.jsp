<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="headerStudy.jsp" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Intro to study session</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.0.6/css/pico.min.css" />
</head>
<body>
<main class="container">
    <h3>Study session ended</h3>

    <p style="color: green">Correct answers: ${correctAnswers}</p>
    <p style="color: darkred">Wrong answers: ${wrongAnswers}</p>

    <c:set var="duration" value="${duration}" />
    <c:set var="hours" value="${duration / 3600}" />
    <c:set var="minutes" value="${(duration % 3600) / 60}" />
    <c:set var="seconds" value="${duration % 60}" />

    <p>Time spent on this session (HH:mm:ss):
        <fmt:formatNumber value="${hours}" pattern="00"/>:<fmt:formatNumber value="${minutes}" pattern="00"/>:<fmt:formatNumber value="${seconds}" pattern="00"/>
    </p>
    <p><a role="button" href="<c:url value='/user/home'/>" autofocus>Good job!</a></p>

</main>
</body>
</html>
