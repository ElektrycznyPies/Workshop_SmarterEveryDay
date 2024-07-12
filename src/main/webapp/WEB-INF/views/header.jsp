<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="color-scheme" content="light dark" />
    <title>Smarter Every Day</title>
    <meta name="description" content="Simple flashcard learning tool." />

    <link
            rel="stylesheet"
            href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.0.6/css/pico.min.css"
    />
</head>

<body>
<header class="container">
    <section>
        <article id="article">
            <h1>Smarter Every Day</h1>
            <h3>Simple flashcard learning tool.</h3>
            <c:choose>
            <c:when test="${empty sessionScope.user}">
                <footer>
                    <small>Login or register to use this tool fully.</small>
                </footer>
            </c:when>
            <c:when test="${sessionScope.user.role == 0}">
                <footer>
                    <small>Hello, <c:out value="${sessionScope.user.firstName}"/>.</small>
                </footer>
            </c:when>
            <c:when test="${sessionScope.user.role == 1}">
                <footer>
                    <small>Admin priviledges. Hello, <c:out value="${sessionScope.user.firstName}"/>.</small>
                </footer>
            </c:when>
            <c:otherwise>
                <footer>
                    <small>Undefined role: <c:out value="${sessionScope.user.role}"/></small>
                </footer>
            </c:otherwise>
            </c:choose>
        </article>
    </section>
    </hgroup>
</header>
</body>
</html>