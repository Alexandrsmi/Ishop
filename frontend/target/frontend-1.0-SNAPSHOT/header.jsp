<%@ page import="ru.ishop.backend.services.Services" %>
<%@ page import="ru.ishop.backend.context.ObjectResolver" %>
<%@ page import="ru.ishop.frontend.session.UserSession" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Services services = ObjectResolver.get("services");
    UserSession userSession = UserSession.getUserSession(request);
%>
