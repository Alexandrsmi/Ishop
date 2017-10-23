package ru.ishop.backend.services;

import ru.ishop.backend.services.image.ImageService;
import ru.ishop.backend.services.order.OrderService;
import ru.ishop.backend.services.product.ProductService;
import ru.ishop.backend.services.user.UserService;

/**
 * Класс сервисов.
 * @author Aleksandr Smirnov.
 */
public class Services {
    /**
     * Поле сервиса пользователя.
     */
    private UserService userService;
    /**
     * Поле сервиса продукта.
     */
    private ProductService productService;
    /**
     * Поле сервиса изображения.
     */
    private ImageService imageService;
    /**
     * Поле сервиса заявки.
     */
    private OrderService orderService;

    /**
     * Конструктор по умолчанию.
     * @param userService - сервис пользователя.
     * @param productService - сервис продуктов.
     * @param imageService - сервис картинок.
     * @param orderService - сервис заявок.
     */
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
