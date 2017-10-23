frc<%@ page import="ru.ishop.backend.services.order.Order" %>
<%@ page import="ru.ishop.backend.services.product.Product" %>
<%@ page import="ru.ishop.backend.services.order.OrderProduct" %>
<%@ page import="ru.ishop.frontend.util.Util" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="page-header.jsp" %>
<%
    if (!userSession.isGuest()) {
        Order order = services.getOrderService().getOrder(Util.getLongParameter(request, "orderId", -1));
%>
<table width="100%">
    <tr>
        <td><h2>Order</h2></td>
        <td style="text-align: right"><a href="history.jsp">Order history</a></td>
    </tr>
</table>

<table cellpadding="10" cellspacing="0">
    <tr>
        <th>#</th>
        <th>Title</th>
        <th>Count</th>
    </tr>
    <% int productNumber = 0;
        for (OrderProduct orderProduct : order.getProducts()) {
            productNumber++;
            Product product = services.getProductService().getProduct(orderProduct.getProductId());
    %>
    <tr>
        <td><%=productNumber%>
        </td>
        <td><a href="product.jsp?productId=<%=product.getId()%>"><%=product.getTitle()%>
        </a></td>
        <td><%=orderProduct.getCount()%></td>

    </tr>
    <%}%>
    <tr class="bucket-total">
        <td></td>
        <td></td>
        <td></td>
    </tr>
</table>
<p>
    Date: <%=order.getDate()%>
    <br/>
    Price: <%=order.getPrice()%>
</p>

<%}%>
<%@ include file="page-footer.jsp" %>
