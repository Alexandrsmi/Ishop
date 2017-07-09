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
<html>
<head>
    <title>Registration</title>
    <style>
        .error {
            color: red;
        }
    </style>
</head>
<body>
<h1>SignIn</h1>
<%if(error){%>
<p class="error">Entered email and password incorrect</p>
<%}%>
<form action="" method="post">
    <table>
        <tr>
            <td>Email</td>
            <td><input name="email" size="10" type="text" value="<%= email!=null?email:""%>"/></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input name="password" type="password" value="<%= password!=null?password:""%>"/></td>
        </tr>
        <tr>
            <td>Save account</td>
            <td><input name="save_account" type="checkbox" <%if (saveAccount){%>checked <%}%>/>
            </td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td style="padding-top: 10px"><input type="submit" value="Login"/></td>
        </tr>
    </table>

</form>
</body>
</html>
<%
        }
    } else {
        response.sendRedirect("/");
    }
%>