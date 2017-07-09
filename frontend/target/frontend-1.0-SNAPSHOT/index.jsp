<%@ page import="ru.ishop.frontend.util.Util" %>
<%@ page import="java.util.List" %>
<%@ page import="ru.ishop.backend.services.product.Product" %>
<%@ page import="ru.ishop.backend.services.order.Order" %>
<%@ page import="ru.ishop.backend.services.order.OrderProduct" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="ru.ishop.backend.services.product.SortField" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@include file="page-header.jsp" %>
<%
    long deletedProductId = Util.getLongParameter(request, "deletedProductId", -1);
    if (deletedProductId != -1) {
        services.getProductService().deleteProduct(deletedProductId);
    }
    int pageSize = Util.PRODUCTS_PAGE_COUNT;
    long productsCount = services.getProductService().getProductsCount();
    long totalPagesCount = (productsCount / pageSize) + 1;
    long pageNumber = Util.getIntParameter(request, "page", 1);
    if (pageNumber > totalPagesCount) {
        pageNumber = totalPagesCount;
    }
    long pageOffset = (pageNumber - 1) * pageSize;
    SortField sortField = SortField.get(request.getParameter("sort"));
    List<Product> productsList = services.getProductService().getProducts(pageOffset, pageSize,sortField);
%>
<%@ include file="page-selector.jsp" %>
<% if (userSession.isAdmin()) {%>
<a href="create-product.jsp">Create new product</a>
<%}%>
<p>
    <form id="viewProductsForm" action="" method="get">
    Sort by
    <select id="sortProductsSelect" onchange="sortProductsList()" name="sort">
        <option value="<%=SortField.no%>">-----</option>
        <option value="<%=SortField.name%>">Name</option>
        <option value="<%=SortField.price%>">Price</option>
    </select>
</form>
</p>
<p>
<table>
    <%
        Order bucket = userSession.isGuest() ? new Order() :
                services.getOrderService().getBucket(userSession.getUser().getId());
        for (Product product : productsList) {%>
    <tr>
        <td><img src="/product-image?productId=<%=product.getId()%>"/></td>
        <td>
            <a href="product.jsp?productId=<%=product.getId()%>">
                <%=product.getTitle()%>
            </a>
            <br/>
            <%=product.getShortDescription()%>
            <br/>
            <%=product.getPrice()%> usd
        </td>
        <% if (!userSession.isGuest()) {%>
        <td>
            <%@include file="page-change-bucket.jsp" %>
        </td>
        <%}%>
        <%if (userSession.isAdmin()) {%>
        <td>
            <form action="" method="post">
                <input type="hidden" value="<%= product.getId()%>" name="deletedProductId"/>
                <input type="submit" value="Delete"/>
                <a href="update-product.jsp?productId=<%=product.getId()%>">Update</a>
            </form>
        </td>
        <%} %>
    </tr>
    <%}%>
</table>
</p>
<%@ include file="page-selector.jsp" %>

<%@include file="page-footer.jsp" %>