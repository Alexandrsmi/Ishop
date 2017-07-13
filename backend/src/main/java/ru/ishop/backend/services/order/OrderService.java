package ru.ishop.backend.services.order;

import ru.ishop.backend.services.product.SortField;

import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public interface OrderService {

    boolean addProduct(long productId, int productCount, long userId);

    boolean updateProductCount(long productId, int productCount, long userId);

    boolean deleteProduct(long productId, long userId);

    Order getBucket(long userId);

    boolean releaseOrder(long userId);

    List<Order> historyOrders(long userId);

    int getBucketProductsCount(long userId);

    Order getOrder(long orderId);

    List<Order> getOrders(long offset, long maxCount);

}
