<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>File Chosen</title>
    <script>
        window.onload = function() {
            window.opener.document.getElementById('${param.field}').value = '${filePath}';
            window.close();
        }
    </script>
</head>
<body>
<p>File chosen: ${filePath}</p>
</body>
</html>