<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<c:if test="${not empty sessionScope.user}">
    <br>
    <form action="${pageContext.request.contextPath}/logout" method="post" enctype="application/x-www-form-urlencoded">
        <button type="submit">Logout</button>
    </form>
</c:if>
</body>
</html>
