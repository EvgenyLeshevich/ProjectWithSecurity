<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Profile</title>
</head>
<body>

<h5>${username}</h5>

<c:if test="${!message}">
    <p>${message}</p>
</c:if>

<form method="post">
    <div><label> Password: <input type="password" name="password" placeholder="Password"/> </label></div>
    <div><label> Email: <input type="email" name="email" placeholder="some@some" value="${email}"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><button type="submit">Save</button></div>
</form>

</body>
</html>
