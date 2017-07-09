<%@ page import="ru.ishop.frontend.util.Util" %>
<%@ page import="ru.ishop.backend.services.product.Product" %>
<%@ page import="ru.ishop.backend.services.order.Order" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="page-header.jsp" %>
<%
    long productId = Util.getLongParameter(request, "productId", -1);
    Product product = services.getProductService().getProduct(productId);
%>
<p>
    <span><img src="/product-image?productId=<%=product.getId()%>"/></span>
    <br/>
    <span>
                <%=product.getTitle()%>
        <br/>
            <%=product.getFullDescription()%>
            <br/>
            <%=product.getPrice()%> usd
        </span>
</p>
<% if (!userSession.isGuest()) {
    Order bucket = services.getOrderService().getBucket(userSession.getUser().getId());
%>
<%@include file="page-change-bucket.jsp" %>
<%}%>
<%@ include file="page-footer.jsp" %>
