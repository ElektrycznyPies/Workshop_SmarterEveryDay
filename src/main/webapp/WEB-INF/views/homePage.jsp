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

    <section>
        <div style="margin-left: 20%; margin-right: 20%;">  <section id="login">
            <details>
                <summary>Login</summary>
                <div class="content">
                    <%@ include file="loginUserComponent.jsp" %>
                </div>
            </details>
        </section>

            <details>
                <summary>Register</summary>
                <div class="content">
                    <section>
                        <p>Register yourself as a new user.</p>
                        <%--        <%@ include file="registerUserComponent.jsp" %> --%>

                        <form:form modelAttribute="user" action="${pageContext.request.contextPath}/register" method="post">
                                                    <form:hidden path="id"/>
                                                    <div>
                                                        <label for="firstName">First Name</label>
                                                        <form:input path="firstName" class="input"/>
                                                    </div>

                                                    <div>
                                                        <label for="lastName">Last Name</label>
                                                        <form:input path="lastName" class="input"/>
                                                    </div>

                                                    <div>
                                                        <label for="email">Email</label>
                                                        <form:input path="email" class="input"/>
                                                    </div>

                                                    <div>
                                                        <label for="password">Password</label>
                                                        <form:input path="password" class="input" type="password"/>
                                                    </div>

                                                    <div>
                                                        <button type="submit">Register</button>
                                                    </div>
                                                </form:form>
                    </section>
                </div>
            </details>
        </div>
    </section>
</main>
</body>
</html>