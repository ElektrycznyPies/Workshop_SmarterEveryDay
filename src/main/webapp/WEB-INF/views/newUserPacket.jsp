<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Create new packet</title>
</head>
<body>
<div class="container">
    <h1>Create New Packet</h1>
    <form:form method="POST" modelAttribute="packet">
        <form:hidden path="id"/>
        <div>
            <form:label path="name">Packet Name:</form:label>
            <form:input path="name"/>
        </div>
        <div>
            <form:label path="description">Description:</form:label>
            <form:textarea path="description"/>
        </div>
        <div>
            <input type="submit" value="Create Packet"/>
        </div>
        <div class="form-group">
            <label for="author">Author:</label>
            <input type="text" id="author" name="author" class="form-control" placeholder="Anonymous">
        </div>
        <div class="form-group">
            <input type="radio" id="useNick" name="authorType" value="nick">
            <label for="useNick">Use nick</label>
            <input type="radio" id="useName" name="authorType" value="name">
            <label for="useName">Use name</label>
        </div>
    </form:form>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const authorInput = document.getElementById('author');
        const useNickRadio = document.getElementById('useNick');
        const useNameRadio = document.getElementById('useName');

        useNickRadio.addEventListener('change', function() {
            if (this.checked) {
                authorInput.value = '${user.nick}';
            }
        });

        useNameRadio.addEventListener('change', function() {
            if (this.checked) {
                authorInput.value = '${user.fullName}';
            }
        });
    });
</script>
</body>
</html>
