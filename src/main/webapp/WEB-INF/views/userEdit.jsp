<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>UserEdit</title>
</head>
<body>
User Editor

<form action="/user" method="post">
    <input type="text" value="${user.username}" name="username">
    <c:forEach items="${roles}" var="role">
        <div>
            <label><input type="checkbox" name="${role}"
            <c:if test="${fn:contains({user.roles}, role)}"> checked="checked"
            </c:if>>
                    ${role}
            </label>
        </div>
    </c:forEach>
    <input type="hidden" value="${user.id}" name="userId">
    <input type="hidden" name="_csrf" value="${_csrf.token}" />
    <button type="submit">Save</button>
</form>
</body>
</html>
