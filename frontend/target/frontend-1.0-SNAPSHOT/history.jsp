<%@ page import="ru.ishop.backend.services.order.Order" %>
<%@ page import="ru.ishop.backend.services.product.Product" %>
<%@ page import="ru.ishop.backend.services.order.OrderProduct" %>
<%@ page import="java.util.List" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="page-header.jsp" %>
<%
    if (!userSession.isGuest()) {
        List<Order> historyOrders = services.getOrderService().historyOrders(userSession.getUser().getId());
%>
<h2>Order history</h2>
<table cellpadding="10" cellspacing="0">
    <tr>
        <th>#</th>
        <th>Date</th>
        <th>Price</th>
        <th>Products</th>
    </tr>
    <%  int orderNumber=0;
        for (Order order : historyOrders) {%>
    <tr onclick="location='order.jsp?orderId=<%=order.getId()%>'"
        onmouseover="this.style.backgroundColor='#dddddd';this.style.cursor='pointer'"
        onmouseout="this.style.backgroundColor='#ffffff';this.style.cursor='default'">
        <td><%=++orderNumber%></td>
        <td><%=order.getDate()%></td>
        <td><%=order.getPrice()%></td>
        <td><%=order.getProductsCount()%></td>
    </tr>
    <%}%>
</table>
<%}%>
<%@ include file="page-footer.jsp" %>
