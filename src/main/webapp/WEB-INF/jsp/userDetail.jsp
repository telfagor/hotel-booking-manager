<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Create order</title>

    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>

<%@include file="header.jsp" %>
<h2>Add your details to make an order!</h2>

<form action="${pageContext.request.contextPath}/userDetail" method="post" enctype="multipart/form-data">
    <label for="tel">Telephone:</label>
    <input type="tel" name="telephone" id="tel" required>
    <br>
    <label for="photo">Select your photo</label>
    <input type="file" name="photo" id="photo">
    <br>
    <label for="birthdate">Birthdate:</label>
    <input type="date" name="birthdate" id="birthdate" required>
    <br>
    <label for="amount">Enter your amount:</label>
    <input type="number" name="amount" min="0" value="0" id="amount">
    <br>
    <button type="submit">Add details</button>
</form>
<c:if test="${not empty requestScope.errors}">
    <c:forEach var="item" items="${requestScope.errors}">
        <div class="error">
            <p>${item.message}</p>
        </div>
    </c:forEach>
</c:if>
</body>
</html>
