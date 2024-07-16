<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - starting page</title>

</head>
<body>
<main class="container">
    <section id="login">
        <details>
            <summary>Login</summary>
            <div class="content">
                <%@ include file="loginUserComponent.jsp" %>
            </div>
        </details>
    </section>
    <section id="register">
            <details>
                <summary>Register</summary>
                <div class="content">
<%--                    <c:set var="user" value="${user}" scope="request"/>--%>
<%--                    <%@ include file="registerUserComponent.jsp" %>--%>






                    <section class="grid">
                        <p>Register yourself as a new user.</p>

                        <%--    <form:form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user">--%>
                        <form:form modelAttribute="user" action="${pageContext.request.contextPath}/register" method="post">
                            <form:hidden path="id"/>

                            <div class="form-group">
                                <label for="firstName">First Name</label>
                                <form:input path="firstName" class="input"/>
                            </div>

                            <div class="form-group">
                                <label for="lastName">Last Name</label>
                                <form:input path="lastName" class="input"/>
                            </div>

                            <div class="form-group">
                                <label for="email">Email</label>
                                <form:input path="email" class="input"/>
                            </div>

                            <div class="form-group">
                                <label for="password">Password</label>
                                <form:input path="password" class="input" type="password"/>
                            </div>

                            <div class="form-group">
<%--                                <input type="submit" value="Register" class="button primary">--%>
                                <button type="submit">Register</button>
                            </div>
                        </form:form>
                    </section>











                </div>
            </details>
        </section>
</main>
</body>