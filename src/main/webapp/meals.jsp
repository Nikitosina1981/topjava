<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://example.com/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
<table>
    <tr align="center">
        <td>Date</td>>
        <td>Description</td>
        <td>Calories</td>
<c:forEach items="${mealsto}" var="element">
    <tr bgcolor="${element.excess ? 'red' : 'green'}" >
        <td>${f:formatLocalDateTime(element.dateTime)}</td>
        <td>${element.description}</td>
        <td>${element.calories}</td>
    </tr>
</c:forEach>
</table>
</body>
</html>
