<%@tag description="User Page template" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@attribute name="userName" required="true"%>

<t:wrapper>
    <jsp:attribute name="header">
      <h1>Hello ${userName}</h1>
    </jsp:attribute>
    <jsp:attribute name="footer">
      <p id="copyright">by Evgeniy L.</p>
    </jsp:attribute>
    <jsp:body>
        <jsp:doBody/>
    </jsp:body>
</t:wrapper>