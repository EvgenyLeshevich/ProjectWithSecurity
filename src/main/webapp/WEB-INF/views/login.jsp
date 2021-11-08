<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="style.css">
    <%--    Сообщаем браузеру что он должен учитывать плотность пикселей на экране утройства и делать крпнее шрифты--%>
    <%--    и элементы интерфейса для моб, планшетов и тд и нормально отображать на стройствах с небольшой плотностью(ноуты компы)--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
</head>
<body>

Login page

<c:if test="${!message}">
    <p>${message}</p>
</c:if>

<form action="/login" method="post">
    <div><label> User Name : <input type="text" name="username"/> </label></div>
    <div><label> Password: <input type="password" name="password"/> </label></div>
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <div><input type="submit" value="Sign In"/></div>
</form>

<a href="/registration">Add new user</a>

</body>
</html>
