<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>

<div>
    <form action="/logout" method="post">
        <input type="submit" value="Sign Out"/>
        <input type="hidden" name="_csrf" value="${_csrf.token}" />
    </form>

    <p><a href="/user">User List</a></p>
</div>

<div>
    <form method="post" enctype="multipart/form-data">
        <input type="text" name="text" placeholder="Введите сообщение"/>
        <input type="text" name="tag" placeholder="Тэг"/>
<%--        Загрузка файлов--%>
        <input type="file" name="file">

        <input type="hidden" name="_csrf" value="${_csrf.token}" />
        <button type="submit">Добавить</button>
    </form>
</div>
<div>Список сообщений</div>

<form method="get" action="/main">
    <input type="text" name="filter" value="${filter}">
    <button type="submit">Найти</button>
</form>

<c:forEach items="${messages}" var="message">
    <div>
        <tr>
            <td>${message.id}</td>
            <td>${message.text}</td>
            <td>${message.tag}</td>
            <td>${message.authorName}</td>
            <div>
                <c:if test="${!message.filename}" >
                <img src="/img/${message.filename}">
                </c:if>
            </div>
            <p></p>
        </tr>
    </div>
</c:forEach>
</body>
</html>
