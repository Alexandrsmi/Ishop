<%@ page import="ru.ishop.backend.services.user.User" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp" %>
<%
    if(userSession.isGuest()){
    boolean registered = false;
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String repeatPassword = request.getParameter("repeatPassword");
    String firstName = request.getParameter("firstName");
    String lastName = request.getParameter("lastName");
    String emailError = null;
    String passwordError = null;
    String firstNameError = null;
    String lastNameError = null;
    String repeatPasswordError = null;
    if (email != null) {
        if (services.getUserService().existsEmail(email)) {
            emailError = "Email all ready exists";
        }
        if (password.isEmpty()) {
            passwordError = "Password must be filled";
        }
        if (!password.equals(repeatPassword)) {
            repeatPasswordError = "Repeat password doesn't equal";
        }
        boolean error = emailError != null || passwordError != null || repeatPasswordError != null;
        if (!error) {
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            if (services.getUserService().createUser(user)) {
                userSession.setUser(user);
                response.sendRedirect("/");
                registered = true;
            }
        }
    }
    if (!registered) {

%>
<!DOCUMENT html>
<html class="registration-page">
<head>
    <meta charset="utf-8">
    <link rel="stylesheet" href="css/shop.css">
</head>
<body>
<div class="container-registration">
    <p>Регистрация</p>
<form action="" method="post">
    <div class="registration-input">
        <input type="text" name="email" placeholder="<%=emailError!=null?emailError:"Введите email"%>"
               value="<%=emailError==null&&email!=null?email:""%>"/>
    </div>
    <div class="registration-input">
        <input type="password" name="password" placeholder="<%=passwordError!=null?passwordError:"Введите пароль"%>"
               value="<%=password!=null?password:""%>"/>
    </div>
    <div class="registration-input">
        <input  type="password" name="repeatPassword"
               placeholder="<%=repeatPasswordError!=null?repeatPasswordError:"Повторите пароль"%>"
               value="<%=repeatPasswordError==null&&repeatPassword!=null?repeatPassword:""%>"/>
    </div>
    <div class="registration-input">
        <input name="firstName" type="text" placeholder="Введите имя" value="<%=firstName!=null?firstName:""%>"/>
    </div>
    <div class="registration-input">
        <input name="lastName" type="text" placeholder="Введите фамилию" value="<%=lastName!=null?lastName:""%>"/>
    </div>
    <div class="registration-input">
        <input class="registration-submit" type="submit" name="submit" value="Регистрация">
    </div>
    <a href="login.jsp">Войти</a>

    <%--<a href="login.html">Войти</a>--%>
    <%--<table>--%>
        <%--<tr>--%>
            <%--<td>Email</td>--%>
            <%--<td><input name="email" size="10" type="text" value="<%= email!=null?email:""%>"/></td>--%>
            <%--<td class="error"><%=emailError != null ? emailError : ""%></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Password</td>--%>
            <%--<td><input name="password" type="password" value="<%= password!=null?password:""%>"/></td>--%>
            <%--<td class="error"><%=passwordError != null ? passwordError : ""%></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Repeat password</td>--%>
            <%--<td><input name="repeatPassword" type="password" value="<%= repeatPassword!=null?repeatPassword:""%>"/></td>--%>
            <%--<td class="error"><%=repeatPasswordError != null ? repeatPasswordError : ""%></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>First name</td>--%>
            <%--<td><input name="firstName" size="10" type="text" value="<%= firstName!=null?firstName:""%>"/></td>--%>
            <%--<td class="error"><%=firstNameError != null ? firstNameError : ""%></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>Last name</td>--%>
            <%--<td><input name="lastName" size="10" type="text" value="<%= lastName!=null?lastName:""%>"/></td>--%>
            <%--<td class="error"><%=lastNameError != null ? lastNameError : ""%></td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
            <%--<td>&nbsp;</td>--%>
            <%--<td style="padding-top: 10px"><input type="submit" value="Register"/></td>--%>
        <%--</tr>--%>
    <%--</table>--%>

</form>
</div>
</body>
</html>
<%
    }
    }else {
        response.sendRedirect("/");
    }
%>