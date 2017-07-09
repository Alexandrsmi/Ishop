package ru.ishop.backend.services;

import ru.ishop.backend.services.image.ImageService;
import ru.ishop.backend.services.order.OrderService;
import ru.ishop.backend.services.product.ProductService;
import ru.ishop.backend.services.user.UserService;

/**
 * @author Aleksandr Smirnov.
 */
public class Services {
    private UserService userService;
    private ProductService productService;
    private ImageService imageService;
    private OrderService orderService;

    public Services(UserService userService, ProductService productService, ImageService imageService, OrderService orderService) {
        this.userService = userService;
        this.productService = productService;
        this.imageService = imageService;
        this.orderService = orderService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public UserService getUserService() {
        return userService;
    }

    public ImageService getImageService() {
        return imageService;
    }

    public OrderService getOrderService() {
        return orderService;
    }
}
