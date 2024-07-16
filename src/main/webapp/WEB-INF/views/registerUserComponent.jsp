<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<section class="grid">
    <p>Register yourself as a new user.</p>

<%--    <form:form action="${pageContext.request.contextPath}/register" method="post" modelAttribute="user">--%>
    <form:form method="post" modelAttribute="user">
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
            <input type="submit" value="Register" class="button primary">
        </div>
    </form:form>
</section>