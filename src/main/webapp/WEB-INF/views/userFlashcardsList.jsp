<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="header.jsp" %>

<html>
<head>
    <title>${isEdit ? 'Edit' : 'Add'} Flashcard</title>
</head>
<body>
<div class="container">
    <div>
        <c:choose>
            <c:when test="${fromwhere == 'user'}">
                <a href="<c:url value='/flashpack/user/packets'/>" class="button">Back to packets list</a>
            </c:when>
            <c:when test="${fromwhere == 'admin'}">
                <a href="<c:url value='/admin/users/all'/>" class="button">Back to users list</a>
            </c:when>
            <c:when test="${fromwhere == 'bazaar'}">
                <a href="<c:url value='/flashpack/bazaar'/>" class="button">Back to Bazaar</a>
            </c:when>
            <c:otherwise>
                <p>"fromwhere" variable problem... fromwhere = ${fromwhere}</p>
            </c:otherwise>
        </c:choose>
            </div>

    <h4>Packet name: ${packetName}</h4>
    <c:choose>
        <c:when test="${empty flashcards}">
        <p>No flashcards in this packet</p>
        <form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
            <button type="submit" class="primary">Add Flashcard</button>
        </form>
    </c:when>
    <c:otherwise>

    <c:if test="${fromwhere == 'user'}">
    <p>Select fields to show during study session
        <a href="#" onclick="alert('These elements will be presented to you during the study session (eg.: if you want to learn birds\' names by their photos, select Image here, but not Name)'); return false;">*</a>
        </p>
        <form action="<c:url value='/flashpack/user/packets/${packetId}/update-study-settings'/>" method="post">
            <div class="grid container">
                <div><label><input type="checkbox" name="showFields" value="name" ${showFields.contains('name') ? 'checked' : ''}> Name</label></div>
                <div><label><input type="checkbox" name="showFields" value="word" ${showFields.contains('word') ? 'checked' : ''}> Word 1</label></div>
                <div><label><input type="checkbox" name="showFields" value="word2" ${showFields.contains('word2') ? 'checked' : ''}> Word 2</label></div>
                <div><label><input type="checkbox" name="showFields" value="additionalText" ${showFields.contains('additionalText') ? 'checked' : ''}> Additional text</label></div>
                <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink') ? 'checked' : ''}> Image</label></div>
<%--                <div><label><input type="checkbox" name="showFields" value="imageLink" ${showFields.contains('imageLink2') ? 'checked' : ''}> Image 2</label></div>--%>
                    </div>
                <p>Select field to check against for correct answer
                    <a href="#" onclick="alert('Your answer will have to match this element (eg.: if you want to learn birds\' names by their photos, select Name here, but not Image)'); return false;">*</a>
                </p>
                <div class="grid container">
                    <div><label><input type="radio" name="compareField" value="name" ${compareField == 'name' ? 'checked' : ''}> Name</label></div>
                    <div><label><input type="radio" name="compareField" value="word" ${compareField == 'word' ? 'checked' : ''}> Word 1</label></div>
                    <div><label><input type="radio" name="compareField" value="word2" ${compareField == 'word2' ? 'checked' : ''}> Word 2</label></div>
                    <div><label><input type="radio" name="compareField" value="additionalText" ${compareField == 'additionalText' ? 'checked' : ''}> Additional text</label></div>
        <%--            <div></div>--%>
                    <div></div>
                </div>

        <div class="grid container">
            <div><button type="submit" class="primary">Save study settings</button></div></form>

    <c:choose>
        <c:when test="${not empty showFields and not empty compareField}">
            <div><a role="button" href="<c:url value='/flashpack/user/packets/${packetId}/study'/>" class="button primary">Start study session</a></div>
        </c:when>
        <c:otherwise>
            <div><button class="primary" disabled>Set fields to study</button></div>
        </c:otherwise>
    </c:choose>


            <div><form action="<c:url value='/flashpack/user/packets/${packetId}/flashcards/add'/>" method="get">
                <button type="submit" class="primary">Add flashcard</button></form></div>

        </div>
</c:if>

                <section class="grid" id="tables">
                    <table class="striped full-width">
                            <thead>
                            <tr>
                                <th scope="col">Name</th>
                                <th scope="col">Word 1</th>
                                <th scope="col">Word 2</th>
                                <th scope="col">Image 1</th>
<%--                                <th scope="col">Image 2</th>--%>
                                <th scope="col">Add. text</th>
                                <th scope="col">Action</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${flashcards}" var="flashcard" varStatus="status">
                                <tr scope="row">
                                    <td><c:out value="${flashcard.name}"/></td>
                                    <td><c:out value="${flashcard.word}"/></td>
                                    <td><c:out value="${flashcard.word2}"/></td>
                                    <td><c:out value="${shortImageLinks[status.index]}"/>
                                    </td>
<%--                                    <td><c:out value="${shortImageLinks2[status.index]}"/>--%>
<%--                                    </td>--%>
                                    <td><c:out value="${shortAdditionalTexts[status.index]}"/></td>
                                    <td>
                                        <c:if test="${fromwhere == 'user'}">
                                            <a href="<c:url value='/flashpack/user/packets/${packetId}/flashcards/edit/${flashcard.id}'/>">Edit</a>
                                        </c:if>
                                        <c:if test="${fromwhere == 'user' or fromwhere == 'admin'}">
                                            <a href="<c:url value='/flashpack/user/packets/${packetId}/flashcards/delete/${flashcard.id}'/>" onclick="return confirm('Are you sure?')">Delete</a>
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                </section>
            </c:otherwise>
        </c:choose>
</div>
</body>
</html>

