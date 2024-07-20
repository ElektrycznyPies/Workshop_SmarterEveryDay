<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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
    <h3>Start study session</h3>
    <form action="/flashpack/user/packets/${packetId}/study" method="post">
        <label for="repetitions">Number of repetitions for each flashcard</label>
        <input type="number" id="repetitions" name="repetitions" min="1" value="1" required>
        <button type="submit" class="primary">Start session</button>
    </form>
</main>
</body>
</html>