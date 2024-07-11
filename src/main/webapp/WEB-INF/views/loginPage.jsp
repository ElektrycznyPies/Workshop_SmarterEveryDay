<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>
<html>
<head>
    <title>Login</title>
</head>
<body>
<main class="container">
    <section id="login">
        <h2>Login / Register</h2>
        <p>
            Please enter your credentials or <a href="/register">register</a> to the site.
        </p>
        <form action="<c:url value='/login'/>" method="post">
            <div class="grid">
                <input
                        type="email"
                        name="email"
                        placeholder="Email address"
                        aria-label="Email address"
                        autocomplete="email"
                        required
                />
                <input
                        type="password"
                        name="password"
                        placeholder="Password"
                        aria-label="Password"
                        required
                />
                <button type="submit">Login</button>
            </div>
        </form>
    </section>
</body>
</html>
