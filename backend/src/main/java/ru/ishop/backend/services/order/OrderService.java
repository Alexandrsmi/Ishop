package ru.ishop.backend.services.order;

import java.util.List;

/**
 * Интерфейс заявок.
 * @author Aleksandr Smirnov.
 */
public interface OrderService {
    /**
     * Метод добавления продукта в заявку.
     * @param productId - id продукта.
     * @param productCount - кол-во продукта.
     * @param userId - id пользователя.
     * @return - true  в случае успешного добавления.
     */
    boolean addProduct(long productId, int productCount, long userId);

    /**
     * Метод изменени кол-ва продукта.
     * @param productId - id продукта.
     * @param productCount - кол-во продукта.
     * @param userId - id пользователя.
     * @return - true  в случае успешного изменения.
     */
    boolean updateProductCount(long productId, int productCount, long userId);

    /**
     * Метод удаления продукта из заявки.
     * @param productId - id продукта.
     * @param userId - id пользователя.
     * @return - true в случае успешного удаления.
     */
    boolean deleteProduct(long productId, long userId);
    /**
     * Метод возвращает заявку по id пользователя.
     * @param userId - id пользователя.
     * @return - возвращаем заявку.
     */
    Order getBucket(long userId);

    /**
     * Метод устанавливает флаг при успешном завершении заявки.
     * @param userId - id пользователя.
     * @return - true в случае успешной заявки.
     */
    boolean releaseOrder(long userId);

    /**
     * Метод возвращает список заявок пользователя.
     * @param userId - id пользователя.
     * @return - список заявок.
     */
    List<Order> historyOrders(long userId);

    /**
     * Метод возвращает кол-во продуктов в корзине.
     * @param userId - id пользователя.
     * @return integer value.
     */
    int getBucketProductsCount(long userId);

    /**
     * Возвращает заявку.
     * @param orderId - id заявки.
     * @return - заявку.
     */
    Order getOrder(long orderId);

    /**
     * Возвращает лист заявок всех пользователей.
     * @return
     */
    List<Order> getOrdersAllUsers();
}