<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Statistics</title>
</head>
<body>
<table>
    <thead>
    <tr>
        <th>Most studied packet(s)</th>
        <th>Hours:minutes</th>
        <th>Avg correct answers</th>
        <th>Avg wrong answers</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach var="entry" items="${sortedPackets}">
        <tr>
            <td>${entry.key.name}</td>
            <td>
                <c:set var="duration" value="${entry.value}" />
                <c:set var="hours" value="${duration / 3600}" />
                <c:set var="minutes" value="${(duration % 3600) / 60}" />
                    ${hours}:${minutes < 10 ? '0' : ''}${minutes}
            </td>
            <td><!-- Wstaw tutaj logikę do obliczenia średnich poprawnych odpowiedzi --></td>
            <td><!-- Wstaw tutaj logikę do obliczenia średnich błędów --></td>
        </tr>
    </c:forEach>
    </tbody>
    <tfoot>
    <tr>
        <td>Total study time:</td>
        <td>
            <c:set var="totalHours" value="${totalDuration / 3600}" />
            <c:set var="totalMinutes" value="${(totalDuration % 3600) / 60}" />
            ${totalHours}:${totalMinutes < 10 ? '0' : ''}${totalMinutes}
        </td>
        <td></td>
        <td></td>
    </tr>
    </tfoot>
</table>
</body>
</html>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<html>--%>
<%--<head>--%>
<%--    <title>Statistics</title>--%>
<%--</head>--%>
<%--<body>--%>
<%--<table>--%>
<%--<thead>--%>
<%--    <tr>--%>
<%--        <th>Most studied packet(s)</th>--%>
<%--        <th>Hours:minutes</th>--%>
<%--        <th>Avg correct answers</th>--%>
<%--        <th>Avg wrong answers</th>--%>
<%--    </tr>--%>
<%--</thead>--%>
<%--<tbody>--%>
<%--    <tr>--%>

<%--        <td>LISTA PAKIETÓW</td>--%>
<%--        <td>ICH CZAS NAUKI</td>--%>
<%--        <td></td>--%>
<%--        <td></td>--%>
<%--    </tr>--%>
<%--</tbody>--%>
<%--<tfoot>--%>
<%--<tr>--%>
<%--    <td>Total study time: </td>--%>
<%--    <td>ZMIENNA TOT.ST.Time</td>--%>
<%--    <td></td>--%>
<%--    <td></td>--%>
<%--</tr>--%>
<%--</tfoot>--%>
<%--</table>--%>

<%--</body>--%>
<%--</html>--%>
