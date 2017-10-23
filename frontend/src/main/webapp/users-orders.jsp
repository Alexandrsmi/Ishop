<%@ page import="ru.ishop.backend.services.order.Order" %>
<%@ page import="java.util.List" %><%--
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="page-header.jsp" %>
<%
    if (!userSession.isGuest()) {
        List<Order> historyOrders = services.getOrderService().getOrdersAllUsers();
%>
<h2>Orders</h2>
    <table cellpadding="10" cellspacing="0" id="thetable">
        <tr>
            <th>#</th>
            <th>Date</th>
            <th>Price</th>
            <th>Products</th>
            <th>Product</th>
            <th>Comment</th>
        </tr>
        <% int orderNumber = 0;
            for (Order order : historyOrders) {%>
        <tr onclick="location='order.jsp?orderId=<%=order.getId()%>'" class="br">
            <%--onmouseover="this.style.backgroundColor='#dddddd';this.style.cursor='pointer'"--%>
            <%--onmouseout="this.style.backgroundColor='#ffffff';this.style.cursor='default'">--%>
            <td><%=++orderNumber%>
            </td>
            <td><%=order.getDate()%>
            </td>
            <td><%=order.getPrice()%>
            </td>
            <td><%=order.getProductsCount()%>
            </td>
            <td class="is-process-cell"><%=order.isProcessed()%>
            </td>
            <td>
            </td>
        </tr>
        <%}%>
    </table>

    <button>Tr</button>
    <script>
//        var table = document.getElementById("thetable");
//        var rows = table.getElementsByTagName("tr");
//        var t = rows.getElementById("t");
//        for (i = 0; i < rows.length; i++) {
//            if (t.innerHTML % 2 == 0) {
//                rows[i].className = "even";
//            }
//        }
var table  = document.getElementById("thetable");
var t = table.getElementsByTagName("tr");
var e = table.getElementsByClassName("is-process-cell")
var num = t.length;
for(var i = 0; i<t.length;i++){
    for (var j = 0; j < e.length; j++) {
        if (!e[j].innerHTML.indexOf("true")) {
            t[j+1].style.backgroundColor = 'red';
        }
    }
}
    <%--</script>--%>
<%--<script>--%>
    <%--var table = documentrt.getElementById("thetable");--%>
    <%--var tds = table.getElementsByTagName("tr");--%>
    <%--for(var i = 0; i < tds.length; i++) {--%>
        <%--tds[i].onmouseover = function() {--%>
            <%--tds[i].style.backgroundColor = "red";--%>
        <%--}--%>
        <%--tds[i].onmouseout = function() {--%>
            <%--this.parentNode.style.backgroundColor = "#fff";--%>
        <%--}--%>
    <%--}--%>
</script>
<%}%>
<%@ include file="page-footer.jsp" %>
