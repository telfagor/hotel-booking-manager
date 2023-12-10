<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Add apartment</title>
</head>
<body>
    <form action="${pageContext.request.contextPath}/addApartment" method="post" enctype="multipart/form-data">
        <label for="number_of_rooms">Number of rooms:</label>
        <input type="number" name="number_of_rooms" id="number_of_rooms">
        <br>
        <label for="number_of_seats">Number of seats:</label>
        <input type="number" name="number_of_seats" id="number_of_seats">
        <br>
        <label for="price_per_hour">Price per hour:</label>
        <input type="number" name="price_per_hour" id="price_per_hour">
        <br>
        <label for="photo">Photo:</label>
        <input type="file" name="photo" id="photo">
        <br>
        <select name="apartment_status">Apartment status:
            <c:forEach var="status" items="${requestScope.statuses}">
                <option value="${status}">${status}</option>
            </c:forEach>
        </select>
        <br>
        <select name="apartment_type">Apartment type:
            <c:forEach var="type" items="${requestScope.types}">
                <option value=${"type"}>${type}</option>
            </c:forEach>
        </select>

        <input type="submit">
    </form>
</body>
</html>
