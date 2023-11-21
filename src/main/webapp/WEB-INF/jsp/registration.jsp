<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <title>Registration</title>
    <style>
        .color-message {
            color: red;
        }
    </style>
</head>
<body>
<h1>Registration</h1>
<form action="${pageContext.request.contextPath}/registration" method="post" enctype="application/x-ww-urlencoded">
    <label for="first_name">First Name:</label>
    <input type="text" name="first_name" id="first_name">
    <br>
    <label for="last_name">Last Name:</label>
    <input type="text" name="last_name" id="last_name">
    <br>
    <label for="email">Email:</label>
    <input type="email" name="email" id="email">
    <br>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password">
    <br>
    <label for="role">Role:</label>
    <select name="role" id="role">
        <c:forEach var="role" items="${requestScope.roles}">
            <option value="${role}" selected>${role}</option>
        </c:forEach>
    </select>
    <br>
    <label>Gender:
        <c:forEach var="gender" items="${requestScope.genders}">
            <input type="radio" name="gender" value="${gender}"> ${gender}
        </c:forEach>
    </label>
    <br>
    <button type="submit">Submit</button>
</form>
    <c:if test="${not empty requestScope.errors}">
        <c:forEach var="error" items="${requestScope.errors}">
            <span class="color-message">${error.message}</span>
        </c:forEach>
    </c:if>
</body>
</html>
