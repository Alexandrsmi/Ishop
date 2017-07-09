<%@ page import="ru.ishop.frontend.util.Util" %>
<%@ page import="ru.ishop.backend.services.product.Product" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="page-header.jsp" %>


<%
    if (userSession.isAdmin()) {
        long productId = Util.getLongParameter(request, "productId", -1);
        Product product = services.getProductService().getProduct(productId);
        String title = request.getParameter("title");
        String shortDescription = request.getParameter("shortDescription");
        String fullDescription = request.getParameter("fullDescription");
        double price = Util.getDoubleParameter(request, "price", -1);
        String titleError = null;
        String shortDescriptionError = null;
        String fullDescriptionError = null;
        String priceError = null;
        boolean error = false;
        if (request.getParameter("updateProduct") != null) {
            if (Util.isBlank(title)) {
                titleError = "Title must be filled";
            }
            if (Util.isBlank(shortDescription)) {
                shortDescriptionError = "Short description must be filled";
            }
            if (Util.isBlank(fullDescription)) {
                fullDescriptionError = "Full description must be filled";
            }
            if (price == -1) {
                priceError = "Price must be filled";
            }

            error = titleError != null || shortDescriptionError != null
                    || fullDescriptionError != null || priceError != null;

            if (!error) {
                product.setTitle(title);
                product.setShortDescription(shortDescription);
                product.setFullDescription(fullDescription);
                product.setPrice(price);
                error = !services.getProductService().updateProduct(product);
            }
        }
%>
<h1>Update product</h1>
<% if (!error && title != null) {%>
<p style="background-color: greenyellow"><a href="product.jsp?productId=<%=product.getId()%>">Product</a> with id
    = <%=product.getId()%> is updated</p>
<%}%>
<form action="" method="post">
    <input type="hidden" name="productId" value="<%=product.getId()%>"/>
    <table>
        <tr>
            <td>Name</td>
            <td><input type="text" name="title" value="<%=title!=null?title:product.getTitle()%>"/></td>
            <td class="error"><%=titleError != null ? titleError : ""%>
            </td>
        </tr>
        <tr>
            <td>Short description</td>
            <td><textarea
                    name="shortDescription"><%=shortDescription != null ? shortDescription : product.getShortDescription()%></textarea>
            </td>
            <td class="error"><%=shortDescriptionError != null ? shortDescriptionError : ""%>
            </td>

        </tr>
        <tr>
            <td>Full description</td>
            <td><textarea
                    name="fullDescription"><%=fullDescription != null ? fullDescription : product.getFullDescription()%></textarea>
            </td>
            <td class="error"><%=fullDescriptionError != null ? fullDescriptionError : ""%>
            </td>

        </tr>
        <tr>
            <td>Price</td>
            <td><input type="text" name="price"
                       value="<%=price!=-1?String.valueOf(price):String.valueOf(product.getPrice())%>"/></td>
            <td class="error"><%=priceError != null ? priceError : ""%>
            </td>
        </tr>
        <tr>
            <td>Image</td>
            <td></td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Update product" name="updateProduct"></td>
        </tr>
    </table>
</form>
<%} else {%>
<script type="application/javascript">
    location = "/";
</script>
<%}%>
<%@include file="page-footer.jsp" %>
