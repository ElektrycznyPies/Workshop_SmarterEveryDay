<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>Add Flashcard</title>
</head>
<body>
<div class="container">
    <div>
        <a href="<c:url value='/user/home'/>" class="button">Main page</a>
    </div>

    <form:form action="/flashpack/user/packets/${packetId}/flashcards/add" method="post" modelAttribute="flashcard">
        <table>
            <tr>
                <td><form:label path="name">Name:</form:label></td>
                <td><form:input path="name"/></td>
            </tr>
            <tr>
                <td><form:label path="word">Word:</form:label></td>
                <td><form:input path="word"/></td>
            </tr>
            <tr>
                <td><form:label path="additionalText">Additional Text:</form:label></td>
                <td><form:textarea path="additionalText"/></td>
            </tr>
            <tr>
                <td><form:label path="imageLink">Image Link:</form:label></td>
                <td>
                    <fieldset role="imglink">
                    <form:input path="imageLink" id="imageLink"/>
                    <button type="button" onclick="openFileChooser('imageLink')">Choose Image</button>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td><form:label path="soundLink">Sound Link:</form:label></td>
                <td>
                    <fieldset role="sndlink">
                    <form:input path="soundLink" id="soundLink"/>
                    <button type="button" onclick="openFileChooser('soundLink')">Choose Sound</button>
                    </fieldset>
                </td>
            </tr>
            <tr>
                <td colspan="2">
                    <button type="submit" class="primary">Add Flashcard</button>
                </td>
            </tr>
        </table>
    </form:form>
    <script>
        function openFileChooser(field) {
            window.open('/choose-file?field=' + field, 'fileChooser', 'width=300,height=100');
        }
    </script>
</div>
</body>
</html>