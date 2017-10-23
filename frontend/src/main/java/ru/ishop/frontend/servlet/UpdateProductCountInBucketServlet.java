package ru.ishop.frontend.servlet;

import ru.ishop.backend.services.Services;
import ru.ishop.frontend.util.Util;

import javax.servlet.http.HttpServletRequest;

/**
 * Класс сервлета отвечает за изменения колличества товара в корзинею.
 * @author Aleksandr Smirnov.
 */
public class UpdateProductCountInBucketServlet extends AbstractBucketProductServlet {

    @Override
    protected void doProcess(long productId, long userId, Services services, HttpServletRequest request) {
        int productCount = Util.getIntParameter(request, "productCount", -1);
        services.getOrderService().updateProductCount(productId, productCount, userId);
    }
}
