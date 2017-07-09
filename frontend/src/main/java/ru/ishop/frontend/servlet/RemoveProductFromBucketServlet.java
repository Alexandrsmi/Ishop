package ru.ishop.frontend.servlet;

import ru.ishop.backend.services.Services;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Aleksandr Smirnov.
 */
public class RemoveProductFromBucketServlet extends AbstractBucketProductServlet{

    @Override
    protected void doProcess(long productId, long userId, Services services,HttpServletRequest request) {
        services.getOrderService().deleteProduct(productId, userId);
    }
}
