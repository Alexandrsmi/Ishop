package ru.ishop.backend.services;

import ru.ishop.backend.context.ObjectResolver;
import ru.ishop.backend.services.order.OrderService;
import ru.ishop.backend.services.product.Product;
import ru.ishop.backend.services.product.ProductService;
import ru.ishop.backend.services.user.UserService;


/**
 * @author Aleksandr Smirnov.
 */
public class AbstractSqlServiceTest {
    private static long salt = System.currentTimeMillis();

    protected String salted(String str) {
        return str + "-" + (salt++);
    }


    protected UserService getUserService() {
        return ObjectResolver.get("userService");
    }

    protected ProductService getProductService() {
        return ObjectResolver.get("productService");
    }

    protected OrderService getOrderService() {
        return ObjectResolver.get("orderService");
    }

}
