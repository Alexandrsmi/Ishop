package ru.ishop.frontend.servlet;

import ru.ishop.backend.services.Services;

import javax.servlet.http.HttpServletRequest;

/**
 * Класс сервлета добавления продукта в корзину.
 * @author Aleksandr Smirnov.
 */
public class AddProductToBucketServlet extends AbstractBucketProductServlet{

    @Override
    protected void doProcess(long productId, long userId, Services services, HttpServletRequest request) {
        services.getOrderService().addProduct(productId, 1, userId);
    }
}
