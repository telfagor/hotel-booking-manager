<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Welcome to our hotel!</h1>
<c:if test="${not empty sessionScope.user}">
    <form action="${pageContext.request.contextPath}/logout" method="post" enctype="application/x-www-form-urlencoded">
        <button type="submit">Logout</button>
    </form>
    <a href="${pageContext.request.contextPath}/userOrders">
        <button type="button">Your orders</button>
    </a>
</c:if>
</body>
</html>
