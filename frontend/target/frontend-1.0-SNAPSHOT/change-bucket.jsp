<%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<button id="addProductToBucket-<%=product.getId()%>" class="addProductToBucket"
        <% if (bucket.containsProduct(product.getId())) {%>
        style="display: none"
        <%}%>
        onclick="addProductToBucket(this,<%=userSession.getUser().getId()%>,<%=product.getId()%>)">+
</button>
<button id="removeProductFromBucket-<%=product.getId()%>" class="addProductToBucket"
        <% if (!bucket.containsProduct(product.getId())) {%>
        style="display: none"
        <%}%>
        onclick="removeProductFromBucket(this,<%=userSession.getUser().getId()%>,<%=product.getId()%>)">-
</button>
<img src="/images/progress.gif" style="display: none" id="changeBucketProgress-<%=product.getId()%>">