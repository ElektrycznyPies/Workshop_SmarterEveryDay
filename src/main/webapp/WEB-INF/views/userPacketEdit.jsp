<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Edit User Packet</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/user/home'/>" class="button">Main page</a>
    </div>

    <form:form method="POST" modelAttribute="packet" action="/flashpack/user/packets/edit">
        <form:hidden path="id"/>
        <div class="form-group">
            <form:label path="name">Packet Name:</form:label>
            <form:input path="name"/>
        </div>
        <div class="form-group">
            <form:label path="description">Description:</form:label>
            <form:textarea path="description"/>
        </div>
        <div class="form-group">
            <label for="author">Author:</label>
            <input type="text" id="author" name="author" class="form-control" value="${packet.author}" placeholder="Anonymous">
        </div>
        <div class="form-group">
            <input type="radio" id="useNick" name="authorType" value="nick">
            <label for="useNick">Use nick</label>
            <input type="radio" id="useName" name="authorType" value="name">
            <label for="useName">Use name</label>
        </div>
        <br>
        <div>
            <input type="submit" value="Update packet"/>
        </div>
    </form:form>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const authorInput = document.getElementById('author');
        const useNickRadio = document.getElementById('useNick');
        const useNameRadio = document.getElementById('useName');

        // ustawiam: początkowy autor
        if (authorInput.value === '${user.firstName}') {
            useNickRadio.checked = true;
        } else if (authorInput.value === '${user.fullName}') {
            useNameRadio.checked = true;
        }

        useNickRadio.addEventListener('change', function() {
            if (this.checked) {
                authorInput.value = '${user.firstName}';
            }
        });

        useNameRadio.addEventListener('change', function() {
            if (this.checked) {
                authorInput.value = '${user.fullName}';
            }
        });
    });
</script></body>
</html>