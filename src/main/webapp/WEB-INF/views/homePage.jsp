<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<jsp:include page="headerAdmin.jsp"></jsp:include>

<html>
<head>
    <title>Smarter Every Day - starting page</title>
</head>
<body>
<main class="container">

    <form>
        <div class="grid">
            <form action="<c:url value='/admin/users/add'/>" method="get">
                <button type="submit" class="primary">About</button>
            </form>
            <form action="<c:url value='/admin/users/add'/>" method="get">
                <button type="submit" class="primary">Login</button>
            </form>
            <form action="<c:url value='/admin/users/add'/>" method="get">
                <button type="submit" class="primary">Register</button>
            </form>
        </div>

        <fieldset>
            <label for="terms">
                <input type="checkbox" role="switch" id="terms" name="terms" />
                I agree to the
                <a href="#" onclick="event.preventDefault()">Privacy Policy</a>
            </label>
        </fieldset>
    </form>
</section>
</main>