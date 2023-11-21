<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Apartments</title>
</head>
<body>
    <h1>Apartments</h1>
    <ul>
        <c:forEach var="apartment" items="${requestScope.apartments}">
            <li>${apartment}</li>
        </c:forEach>
    </ul>
</body>
</html>
