<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<p><%if (pageNumber > 1) {%>
    <a href="?page=<%=1%>">&nbsp;<<</a>
    <a href="?page=<%=pageNumber-1%>">&nbsp;<</a>
    <%}%>
    <span><%=pageNumber%> / <%=totalPagesCount%></span>
    <%if (pageNumber < totalPagesCount) {%>
    <a href="?page=<%=pageNumber+1%>">&nbsp;></a>
    <a href="?page=<%=totalPagesCount%>">&nbsp;>></a>
    <%}%>
    <br/>
    <%=productsCount%> products
</p>