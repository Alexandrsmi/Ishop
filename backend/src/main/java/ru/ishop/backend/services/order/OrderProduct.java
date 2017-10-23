package ru.ishop.backend.services.order;

import ru.ishop.backend.data.Id;

/**
 * Класс продуктов в заявке.
 * @author Aleksandr Smirnov.
 */
public class OrderProduct extends Id {
    /**
     * Поле id продукта.
     */
    private long productId;
    /**
     * Поле кол-ва продукта.
     */
    private int count;
    /**
     * Поле id заявки.
     */
    private long orderId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }
}
