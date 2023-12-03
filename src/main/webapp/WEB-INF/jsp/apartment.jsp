<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Apartments</title>
    <style>
        .apartment-description {
            list-style-type: none;
            margin-bottom: 20px;
        }

        .btn {
            display: inline-block;
            margin: 40px;
        }
    </style>
</head>
<body>
    <%@ include file="header.jsp"%>
    <h2>Apartments</h2>
    <ul>
        <c:forEach var="apartment" items="${requestScope.apartments}">
            <c:if test="${apartment.status != 'OCCUPIED'}">
            <li>
                <ul class="apartment-description">
                    <li><img width="300" src="${pageContext.request.contextPath}/images/${apartment.photo}" alt="apartment"></li>
                    <li>apartment identifier: ${apartment.id}</li>
                    <li>number of rooms: ${apartment.numberOfRooms}</li>
                    <li>number of seats: ${apartment.numberOfSeats}</li>
                    <li>price per hour: $${apartment.pricePerHour}</li>
                    <li>status: ${apartment.status}</li>
                    <li>type: ${apartment.type}</li>
                </ul>
            </li>
            </c:if>
        </c:forEach>
    </ul>
    <a href="${pageContext.request.contextPath}/login">
        <button class="btn" type="button">Back</button>
    </a>

    <c:choose>
        <c:when test="${sessionScope.user.userDetail ne null}">
            <a href="${pageContext.request.contextPath}/order">
                <button class="btn" type="button">Create order</button>
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/userDetail">
                <button class="btn" type="button">Create order</button>
            </a>
        </c:otherwise>
    </c:choose>
</body>
</html>
