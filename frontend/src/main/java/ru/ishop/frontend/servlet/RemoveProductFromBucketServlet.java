package ru.ishop.frontend.servlet;

import ru.ishop.backend.services.Services;

import javax.servlet.http.HttpServletRequest;

/**
 * Класс сервлета отвечает за удаления продукта из корзины.
 * @author Aleksandr Smirnov.
 */
public class RemoveProductFromBucketServlet extends AbstractBucketProductServlet{

    @Override
    protected void doProcess(long productId, long userId, Services services,HttpServletRequest request) {
        services.getOrderService().deleteProduct(productId, userId);
    }
}
