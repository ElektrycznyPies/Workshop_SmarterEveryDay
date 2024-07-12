<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section id="login">
    <p>
        Please enter your credentials.
    </p>
<%--    <form action="<c:url value='/login'/>" method="post">--%>
    <form action="${pageContext.request.contextPath}/login" method="post">
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