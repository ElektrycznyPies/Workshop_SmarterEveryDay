<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="color-scheme" content="light dark" />
    <title>Smarter Every Day</title>
    <meta name="description" content="Simple flashcard learning tool." />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.0.6/css/pico.min.css" />

    <%--PŁYNNE ROZWIJANIE:--%>
    <%--    <style>--%>
<%--        details .content {--%>
<%--            max-height: 0;--%>
<%--            overflow: hidden;--%>
<%--            transition: max-height 0.5s ease-out;--%>
<%--        }--%>
<%--        summary .content {--%>
<%--            max-height: 0;--%>
<%--            overflow: hidden;--%>
<%--            transition: max-height 0.5s ease-out;--%>
<%--        }--%>

<%--        details[open] .content {--%>
<%--            max-height: 1500px; /* dostosować */--%>
<%--            transition: max-height 0.5s ease-in;--%>
<%--        }--%>
<%--        details .content {--%>
<%--            max-height: 500px; /* dostosować */--%>
<%--            transition: max-height 0.5s ease-in;--%>
<%--        }--%>
<%--    </style>--%>
</head>
<body>
<header class="container">
    <section>
        <article id="article">
            <h1>Smarter Every Day</h1>
            <h3>Simple flashcard learning tool.</h3>
            <c:choose>

                <%--                NIKT NIE JEST ZALOGOWANY--%>
                <c:when test="${empty sessionScope.user}">
                <%--LUB LEPIEJ:  <c:when test="${sessionScope.loggedIn == true}">--%>
                <footer>
                        <div class="grid">
                            <div>
                                <small>Login or register to use this tool fully.</small>
                            </div>
                            <div style="text-align: right;">
                            </div>
                            <div style="text-align: right;">
                                <c:choose>
                                    <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                        <small><a href="${prevUrl}" class="button">Back</a></small>
                                    </c:when>
                                    <c:otherwise>
                                        <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </footer>
                </c:when>

                <%--                ZALOGOWANY ZWYKŁY USER--%>
                <c:when test="${sessionScope.user.role == 0}">
                    <footer>
                        <div class="grid">
                            <div style="width: 70%;">
                                <small>Hello, <c:out value="${sessionScope.user.firstName}"/>.</small>
                            </div>
                            <div style="text-align: right;">
                                        <small><a href="<c:url value='/logout'/>">Logout</a></small>
                            </div>
                            <div class="grid" style="text-align: right;">
                                <c:choose>
                                    <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                        <small><a href="${prevUrl}" class="button">Back</a></small>
                                    </c:when>
                                    <c:otherwise>
                                        <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </footer>
                </c:when>

                <c:when test="${sessionScope.user.role == 1}">
                    <footer>
                        <div class="grid">
                            <div style="width: 70%;">
                                <small><a href="<c:url value='/user/home#admin'/>">ADMIN privileges</a>. Hello, <c:out value="${sessionScope.user.firstName}"/>.</small>
                            </div>
                            <div style="text-align: right;">
                                    <small><a href="<c:url value='/logout'/>">Logout</a></small>
                            </div>
                            <div style="text-align: right;">
                                <c:choose>
                                    <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                        <small><a href="${prevUrl}" class="button">Back</a></small>
                                    </c:when>
                                    <c:otherwise>
                                        <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </footer>
                </c:when>

                <c:otherwise>
                    <!-- inne przyszłe role -->
                </c:otherwise>
            </c:choose>
        </article>
    </section>
</header>

<!-- Skrypt JavaScript -->
<%--<script>--%>
<%--    document.addEventListener('DOMContentLoaded', function() {--%>
<%--        document.querySelectorAll('details').forEach((el) => {--%>
<%--            const summary = el.querySelector('summary');--%>
<%--            const content = el.querySelector('.content');--%>
<%--            el.open = false;  // Ensure it's closed initially--%>
<%--            summary.addEventListener('click', (e) => {--%>
<%--                e.preventDefault();--%>
<%--                if (el.open) {--%>
<%--                    el.open = false;--%>
<%--                    requestAnimationFrame(() => {--%>
<%--                        content.style.maxHeight = content.scrollHeight + 'px';--%>
<%--                        requestAnimationFrame(() => {--%>
<%--                            content.style.maxHeight = '0';--%>
<%--                        });--%>
<%--                    });--%>
<%--                } else {--%>
<%--                    el.open = true;--%>
<%--                    content.style.maxHeight = content.scrollHeight + 'px';--%>
<%--                }--%>
<%--            });--%>
<%--        });--%>
<%--    });--%>
<%--</script>--%>
</body>
</html>
