<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <link href="../static/css/style.css" type="text/css" rel="stylesheet" >
    <%--    Сообщаем браузеру что он должен учитывать плотность пикселей на экране утройства и делать крпнее шрифты--%>
    <%--    и элементы интерфейса для моб, планшетов и тд и нормально отображать на стройствах с небольшой плотностью(ноуты компы)--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.3/css/bootstrap.min.css" integrity="sha384-MCw98/SFnGE8fJT3GXwEOngsV7Zt27NXFoaoApmYm81iuXoPkFOJwJ8ERdknLPMO" crossorigin="anonymous">
    <script src="https://www.google.com/recaptcha/api.js" async defer></script>
</head>
<body>

Add new user
<c:if test="${!message}">
    <p>${message}</p>
</c:if>

<form:form action="/registration" modelAttribute="user"  method="post">

    <div>
        <form:label path="username">Username:</form:label>
        <form:input path="username" id="username"/>
        <form:errors path="username" style="color:red"/>
    </div>

    <div>
        <form:label path="password">Password:</form:label>
        <form:password path="password"/>
        <form:errors path="password" style="color:red"/>
    </div>
    <div>
        <form:label path="passwordTwo">Password Two:</form:label>
        <form:password path="passwordTwo"/>
        <form:errors path="passwordTwo" style="color:red"/>
            ${passwordTwoError}
    </div>

    <div>
        <form:label path="email">Email:</form:label>
        <form:input path="email" id="email"/>
        <form:errors path="email" style="color:red"/>
    </div>

    <div>
        <div class="g-recaptcha" data-sitekey="6LfgCCIdAAAAANcFcRe8ONSBwwdlyzPRat8i78ko"></div>
        ${captchaError}
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign Up"/></div>
</form:form>

</body>
</html>
