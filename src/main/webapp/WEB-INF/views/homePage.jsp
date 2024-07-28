<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>
<html>
<head>
    <title>Smarter Every Day - starting page</title>

</head>
<body>
<main>

    <div class="grid container">
        <section id="login">
            <p>Enter your credentials</p>
            <div>
                <%@ include file="loginUserComponent.jsp" %>
            </div>
        </section>

        <section id="register">
            <p>Register yourself as a new user</p>
            <div>
                <%--        <%@ include file="@@gisterUserComponent.jsp" %> --%>
                <form:form modelAttribute="user" action="${pageContext.request.contextPath}/register" method="post">
                    <form:hidden path="id"/>
                    <div>
                        <label for="firstName">First Name<small>*</small></label>
                        <form:input path="firstName" class="input" required="required"/>
                    </div>

                    <div>
                        <label for="lastName">Last Name<small>*</small></label>
                        <form:input path="lastName" class="input" required="required"/>
                    </div>
                    <div>
                        <label for="nick">Nick</label>
                        <form:input path="nick" class="input"/>
                    </div>

                    <div>
                        <label for="email">Email<small>*</small></label>
                        <form:input path="email" class="input" required="required"/>
                    </div>

                    <div>
                        <label for="password">Password<small>*</small></label>
                        <form:input path="password" class="input" type="password" required="required"/>
                    </div>

                    <div>
                        <button type="submit">Register</button>
                    </div>
                </form:form>
            </div>
        </section>
    </div>
    <hr>
    <div class="container">
        <section id="logadmin">
            <form action="${pageContext.request.contextPath}/login" method="post">
                <fieldset role="group">
                    <div>
                        <input type="email" name="email" placeholder="Email address" aria-label="Email address" autocomplete="email" value="dpiot@x.com"/>
                    </div>
                    <div>
                        <input type="password" name="password" placeholder="Password" aria-label="Password" value="dpiot"  />
                    </div>
                    <button type="submit">QuickLog Admin</button>
                </fieldset>
            </form>
        </section>
    </div>
    <hr>

</main>
</body>
</html>