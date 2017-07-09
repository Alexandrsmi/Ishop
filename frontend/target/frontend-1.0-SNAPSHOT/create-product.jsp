<%@ page import="ru.ishop.frontend.util.UploadProductInfo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="page-header.jsp" %>
<%
    if (userSession.isAdmin()) {
        UploadProductInfo.UploadProductInfoResult result = null;
        if (request.getContentType() != null && request.getContentType().contains("multipart/form-data")) {
            result = UploadProductInfo.uploadProduct(request);
        }

%>
<h1>Create product</h1>
<% if (result != null && !result.isError()) {%>
<p style="background-color: greenyellow">New <a
        href="product.jsp?productId=<%=result.getProduct().getId()%>">product</a> with id
    = <%=result.getProduct().getId()%> is created</p>
<%}%>
<form action="" method="post" enctype="multipart/form-data">
    <table>
        <tr>
            <td></td>
            <td><input type="file" name="image"/></td>
            <td></td>
        </tr>
        <tr>
            <td>Name</td>
            <td><input type="text" name="title" value="<%=result!=null &&
             result.getProduct().getTitle()!=null?result.getProduct().getTitle():""%>"/></td>
            <td class="error"><%=result != null && result.getTitleError() != null ? result.getTitleError() : ""%>
            </td>
        </tr>
        <tr>
            <td>Short description</td>
            <td><textarea
                    name="shortDescription"><%= result != null && result.getProduct().getShortDescription() != null ? result.getProduct().getShortDescription() : ""%></textarea>
            </td>
            <td class="error"><%=result != null && result.getShortDescriptionError() != null ? result.getShortDescriptionError() : ""%>
            </td>

        </tr>
        <tr>
            <td>Full description</td>
            <td><textarea
                    name="fullDescription"><%= result != null && result.getProduct().getFullDescription() != null ? result.getProduct().getFullDescription() : ""%></textarea>
            </td>
            <td class="error"><%=result != null && result.getFullDescriptionError() != null ? result.getFullDescriptionError() : ""%>
            </td>

        </tr>
        <tr>
            <td>Price</td>
            <td><input type="text" name="price"
                       value="<%=result!=null&&result.getProduct().getPrice()!=-1?String.valueOf(result.getProduct().getPrice()):""%>"/>
            </td>
            <td class="error"><%=result != null && result.getPriceError() != null ? result.getPriceError() : ""%>
            </td>
        </tr>
        <tr>
            <td></td>
            <td><input type="submit" value="Create product" name="createProduct"></td>
        </tr>
    </table>
</form>
<%} else {%>
<script type="application/javascript">
    location = "/";
</script>
<%}%>
<%@include file="page-footer.jsp" %>
