<!DOCTYPE html>
<html lang="pl">
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <meta name="color-scheme" content="light dark" />
    <title>Mind Fishka</title>
    <meta name="description" content="Simple flashcard learning tool." />
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@picocss/pico@2.0.6/css/pico.min.css" />
</head>
<body>
<header class="container">
    <section>
        <article id="article">
            <h1>Study session</h1>

            <c:choose>

                <%--  NIKT NIE JEST ZALOGOWANY lub nieznana rola--%>
                <c:when test="${empty sessionScope.user && sessionScope.user.role != 0 && sessionScope.user.role != 1}">

                    <footer>
                        <div class="grid">
                            <div class="col-6">
                                <small>Login or register to use this tool fully</small>
                            </div>
                            <div class="col-2" align="right">
                            </div>
                            <div class="col-2" align="right">
                            </div>
                            <div class="col-2" align="right">
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

                <%--  ZALOGOWANY ZWYKŁY USER--%>
                <c:when test="${sessionScope.user.role == 0}">
                    <footer>
                        <div class="grid">
                            <div class="col-6">
                                <small>Hello, <c:out value="${sessionScope.user.firstName}"/></small>
                            </div>
                            <div class="col-2" align="right">
                                    <%--       LINKI JAKO BUTTONY:  <small tabindex="1"><a role="button" href="<c:url value='/user/home'/>">Main page</a></small>--%>
                                <small tabindex="1"><a role="button" href="<c:url value='/user/home'/>">Main page</a></small>
                            </div>
                            <div class="col-2" align="right">
                                <small><a href="<c:url value='/logout'/>">Logout</a></small>
                            </div>
                            <div class="col-2" align="right">
                                <c:choose>
                                    <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                        <small><a href="${prevUrl}">Back</a></small>
                                    </c:when>
                                    <c:otherwise>
                                        <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </footer>
                </c:when>
                <%--               ZALOGOWANY ADMIN--%>
                <c:when test="${sessionScope.user.role == 1}">
                    <footer>
                        <div class="grid">
                            <div class="col-6">
                                <small><a href="<c:url value='/user/home#admin'/>">ADMIN</a> privileges. Hello, <c:out value="${sessionScope.user.firstName}"/></small>
                            </div>
                            <div class="col-2" align="right">
                                <small><a href="<c:url value='/user/home'/>">Main page</a></small>
                            </div>
                            <div class="col-2" align="right">
                                <small><a href="<c:url value='/logout'/>">Logout</a></small>
                            </div>
                            <div class="col-2" align="right">
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
</body>
</html>
