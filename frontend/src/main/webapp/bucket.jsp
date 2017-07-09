<%@ page import="ru.ishop.backend.services.order.Order" %>
<%@ page import="ru.ishop.backend.services.product.Product" %>
<%@ page import="ru.ishop.backend.services.order.OrderProduct" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="page-header.jsp" %>
<%
    if (!userSession.isGuest()) {
        Order bucket = services.getOrderService().getBucket(userSession.getUser().getId());

%>
<table width="100%">
    <tr>
        <td><h2>Bucket</h2></td>
        <td style="text-align: right"><a href="history.jsp">Order history</a></td>
    </tr>
</table>

<table cellpadding="10" cellspacing="0">
    <tr>
        <th>#</th>
        <th>Title</th>
        <th>Count</th>
        <th>Price</th>
        <th>Total price</th>
        <th></th>
    </tr>
    <% int productNumber = 0;
        double totalPrice = 0;
        for (OrderProduct orderProduct : bucket.getProducts()) {
            productNumber++;
            Product product = services.getProductService().getProduct(orderProduct.getProductId());
            double productPrice = product.getPrice() * orderProduct.getCount();
            totalPrice += productPrice;
    %>
    <tr>
        <td><%=productNumber%>
        </td>
        <td><a href="product.jsp?productId=<%=product.getId()%>"><%=product.getTitle()%>
        </a></td>
        <td><input id="count-<%=product.getId()%>" type="text" value="<%=orderProduct.getCount()%>" size="3"/>
            <button onclick="updateProductCountInBucket(<%=product.getId()%>)">Set</button>
        </td>
        <td><span id="price-<%=product.getId()%>"><%=product.getPrice()%></span>
        </td>
        <td><span class="totalProductPrice" id="totalProductPrice-<%=product.getId()%>"> <%=productPrice%></span>
        </td>
        <td>
            <button class="deleteProductButton" onclick="removeProductFromBucketTable(<%=product.getId()%>)">Delete
            </button>
        </td>
    </tr>
    <%}%>
    <tr class="bucket-total">
        <td></td>
        <td></td>
        <td></td>
        <td></td>
        <td><span id="totalPrice"><%=totalPrice%></span>
        </td>
    </tr>
</table>
<% if (!bucket.getProducts().isEmpty()) {%>
<p>
    <button onclick="releaseOrder(this);">Finish order</button>
</p>
<%}%>
<%}%>
<%@ include file="page-footer.jsp" %>
