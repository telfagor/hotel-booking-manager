<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Order</title>

    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp"%>
    <h2>Create order!</h2>

    <form action="${pageContext.request.contextPath}/order" method="post" enctype="application/x-www-form-urlencoded">
        <label for="apartment">Apartment:</label>
        <select name="apartment" id="apartment">
            <c:forEach var="apartment" items="${requestScope.apartments}">
                <c:if test="${apartment.status ne 'OCCUPIED'}">
                    <option value="${apartment.id}">${apartment.id}</option>
                </c:if>
            </c:forEach>
        </select>
        <br>
        <label for="check-in">Start to stay:</label>
        <input type="datetime-local" name="check-in" value="${requestScope.current}" id="check-in">
        <br>
        <label for="check-out">End to stay:</label>
        <input type="datetime-local" name="check-out" id="check-out">
        <br>
        <button type="submit">Create Order</button>
    </form>
    <c:if test="${not empty requestScope.errors}">
        <c:forEach var="error" items="${requestScope.errors}">
            <span class="error">${error.message}</span>
        </c:forEach>
    </c:if>
</body>
</html>
