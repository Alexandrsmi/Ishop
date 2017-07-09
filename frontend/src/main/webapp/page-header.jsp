<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="header.jsp"%>
<%
    if (request.getParameter("logout")!=null){
        userSession.setUser(null);
    }
%>
<html>
<head>
    <title>Ishop</title>
    <link href="css/shop.css" rel="stylesheet" />
    <script src="js/shop.js"></script>
</head>
<body>
<table width="100%">
    <tr>
        <td><a href="/"><b>Ishop</b></a>
           </td>
        <td style="text-align: right">
<%if(userSession.isGuest()){%>
         <a href="login.jsp">Sign In</a>
            |
         <a href="registration.jsp">Registration</a>
<%}else {%>
    <span><%=userSession.getUser().getFirstName()%> <%=userSession.getUser().getLastName()%></span>
            |
            <a href="bucket.jsp">Bucket (<span id="bucket_size"><%=services.getOrderService().getBucketProductsCount(userSession.getUser().getId())%></span>)</a>
            |
            <a href="?logout">Logout</a>
<%}%>
        </td>
    </tr>
</table>
<hr/>