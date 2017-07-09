package ru.ishop.frontend.servlet;

import ru.ishop.backend.context.ObjectResolver;
import ru.ishop.backend.services.Services;
import ru.ishop.frontend.session.UserSession;
import ru.ishop.frontend.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author Aleksandr Smirnov.
 */
public abstract class AbstractBucketProductServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        long productId = Util.getLongParameter(req, "productId", -1);
        Services services = ObjectResolver.get("services");
        UserSession userSession = UserSession.getUserSession(req);
        long userId = userSession.getUser().getId();
        doProcess(productId,userId,services,req);
        try (PrintWriter writer = resp.getWriter()) {
            int productsCount = services.getOrderService().getBucketProductsCount(userId);
            writer.write(String.valueOf(productsCount));
            writer.flush();
        }
    }

    protected abstract void doProcess(long productId,long userId,Services services,HttpServletRequest request);
}
