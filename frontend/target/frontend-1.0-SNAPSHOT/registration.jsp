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
<html>
<head>
    <title>Registration</title>
    <style>
        .error{color: red;}
    </style>
</head>
<body>
<h1>Registration</h1>
<form action="" method="post">
    <table>
        <tr>
            <td>Email</td>
            <td><input name="email" size="10" type="text" value="<%= email!=null?email:""%>"/></td>
            <td class="error"><%=emailError != null ? emailError : ""%></td>
        </tr>
        <tr>
            <td>Password</td>
            <td><input name="password" type="password" value="<%= password!=null?password:""%>"/></td>
            <td class="error"><%=passwordError != null ? passwordError : ""%></td>
        </tr>
        <tr>
            <td>Repeat password</td>
            <td><input name="repeatPassword" type="password" value="<%= repeatPassword!=null?repeatPassword:""%>"/></td>
            <td class="error"><%=repeatPasswordError != null ? repeatPasswordError : ""%></td>
        </tr>
        <tr>
            <td>First name</td>
            <td><input name="firstName" size="10" type="text" value="<%= firstName!=null?firstName:""%>"/></td>
            <td class="error"><%=firstNameError != null ? firstNameError : ""%></td>
        </tr>
        <tr>
            <td>Last name</td>
            <td><input name="lastName" size="10" type="text" value="<%= lastName!=null?lastName:""%>"/></td>
            <td class="error"><%=lastNameError != null ? lastNameError : ""%></td>
        </tr>
        <tr>
            <td>&nbsp;</td>
            <td style="padding-top: 10px"><input type="submit" value="Register"/></td>
        </tr>
    </table>

</form>
</body>
</html>
<%
    }
    }else {
        response.sendRedirect("/");
    }
%>