<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserList</title>
</head>
<body>
List of users
<table>
    <thead>
    <tr>
        <th>Name</th>
        <th>Role</th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${users}" var="user">
        <tr>
            <td>${user.username}</td>
            <td><c:forEach items="${user.roles}" var="role">${role} </c:forEach></td>
            <td><a href="/user/${user.id}">Edit</a> </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
