<%@ page import="ru.ishop.backend.services.user.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%
    if (userSession.isGuest()) {
        boolean error = false;
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean saveAccount = request.getParameter("save_account")!=null;
        if (email != null) {
            User user = services.getUserService().getUser(email, password);
            if (user != null) {
                userSession.setUser(user);
                response.sendRedirect("/");
            } else {
                error = true;
            }
        }
        if (userSession.isGuest()) {
%>
<html id="login-t">
<head>
    <title>Registration</title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/shop.css">
</head>
<body>
<a href="index.jsp">Index</a>
<div class="container-login">
<h1 id="authorization-login">SignIn</h1>
<%if(error){%>
<p class="error">Entered email and password incorrect</p>
<%}%>
<form action="" method="post">
    <div class="login-input">
        <input type="text" name="email"  placeholder="Введите логин" value="<%= email!=null?email:""%>"></div>
    <div  class="login-input">
        <input type="password" name="password"  placeholder="Введите пароль" value="<%= password!=null?password:""%>">
    </div>
    <div  class="login-input">
        <input class="login-submit" type="submit" name="submit" value="Войти">
    </div>
    <a href="registration.jsp" >Регистрация</a>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>Email</td>--%>
            <%--<td><input name="email" size="10" type="text" value="<%= email!=null?email:""%>"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Password</td>--%>
            <%--<td><input name="password" type="password" value="<%= password!=null?password:""%>"/></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Save account</td>--%>
            <%--<td><input name="save_account" type="checkbox" <%if (saveAccount){%>checked <%}%>/>--%>
            <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td style="padding-top: 10px"><input type="submit" value="Login"/></td>--%>
        <%--</tr>--%>
    <%--</table>--%>

</form>
</div>
</body>
</html>
<%
        }
    } else {
        response.sendRedirect("/");
    }
%>