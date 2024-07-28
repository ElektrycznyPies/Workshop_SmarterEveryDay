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
            <h1>Mind Fishka</h1>
            <h4>Simple flashcard learning tool</h4>

            <c:choose>
                <%--  NIKT NIE JEST ZALOGOWANY lub nieznana rola--%>
                <c:when test="${empty sessionScope.user && sessionScope.user.role != 0 && sessionScope.user.role != 1}">
                    <footer>
                        <table style="width: 100%; border-collapse: collapse; border: none; background-color: transparent;">

                            <tr>
                                <td colspan="2">
                                    <small>Login or register to use this tool fully</small>
                                </td>
                                <td align="right">
                                </td>
                                <td align="right">
                                </td>
                                <td align="right">
                                    <c:choose>
                                        <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                            <small><a href="${prevUrl}" class="button">Back</a></small>
                                        </c:when>
                                        <c:otherwise>
                                            <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </footer>
                </c:when>

                <%--  ZALOGOWANY ZWYKŁY USER--%>
                <c:when test="${sessionScope.user.role == 0}">
                    <footer>
                        <table style="width: 100%; border-collapse: collapse;">
                            <tr>
                                <td colspan="2">
                                    <small>Hello, <c:out value="${sessionScope.user.firstName}"/></small>
                                </td>
                                <td align="right">
                                    <small tabindex="1"><a href="<c:url value='/user/home'/>">Main page</a></small>
                                </td>
                                <td align="right">
                                    <small><a href="<c:url value='/logout'/>">Logout</a></small>
                                </td>
                                <td align="right">
                                    <small><a href="<c:url value='/flashpack/bazaar'/>">Packets Bazaar</a></small>
                                </td>
                                <td align="right">
                                    <c:choose>
                                        <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                            <small><a href="${prevUrl}">Back</a></small>
                                        </c:when>
                                        <c:otherwise>
                                            <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
                    </footer>
                </c:when>

                <%--  ZALOGOWANY ADMIN--%>
                <c:when test="${sessionScope.user.role == 1}">
                    <footer>
                        <table style="width: 100%; border-collapse: collapse;">
                            <tr>
                                <td colspan="2">
                                    <small>(<a href="<c:url value='/admin/users/all'/>">ADMIN</a>) Hello, <c:out value="${sessionScope.user.firstName}"/></small>
                                </td>
                                <td align="right">
                                    <small><a href="<c:url value='/user/home'/>">Main page</a></small>
                                </td>
                                <td align="right">
                                    <small><a href="<c:url value='/logout'/>">Logout</a></small>
                                </td>
                                <td align="right">
                                    <small><a href="<c:url value='/flashpack/bazaar'/>">Packets Bazaar</a></small>
                                </td>
                                <td align="right">
                                    <c:choose>
                                        <c:when test="${pageContext.request.servletPath eq '/WEB-INF/views/aboutPage.jsp'}">
                                            <small><a href="${prevUrl}" class="button">Back</a></small>
                                        </c:when>
                                        <c:otherwise>
                                            <small><a href="<c:url value='/about'/>" class="button">About</a></small>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                        </table>
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
