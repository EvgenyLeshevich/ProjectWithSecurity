<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
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
    <form:errors path="username" style="color:red" />
    </div>

    <div>
        <form:label path="password">Password:</form:label>
        <form:password path="password" />
        <form:errors path="password" style="color:red" />
    </div>
    <div>
        <form:label path="passwordTwo">Password Two:</form:label>
        <form:password path="passwordTwo" />
        <form:errors path="passwordTwo" style="color:red" />
        ${passwordTwoError}
    </div>

    <div>
    <form:label path="email">Email:</form:label>
    <form:input path="email" id="email"/>
    <form:errors path="email" style="color:red" />
    </div>

    <input type="hidden" name="_csrf" value="${_csrf.token}"/>
    <div><input type="submit" value="Sign In"/></div>
</form:form>

</body>
</html>
