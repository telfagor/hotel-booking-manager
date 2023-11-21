<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/login" method="post" enctype="application/x-www-form-urlencoded">
    <label for="email">Email:</label>
    <input type="email" name="email" id="email" value="${param.email}" required>
    <br>
    <label for="password">Password:</label>
    <input type="password" name="password" id="password" required>
    <br>
    <button type="submit">Login</button>
</form>
<div>
    <p>Did you not have an account yet?
        <a href="${pageContext.request.contextPath}/registration">
            <button type="button">Registration</button></a>
    </p>
    <div>
        <span>
            <c:if test="${param.error != null}">
                <p>Email or password is not correct!</p>
            </c:if>
        </span>
    </div>
</div>
</body>
</html>
