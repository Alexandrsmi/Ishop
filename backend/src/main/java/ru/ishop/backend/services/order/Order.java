package ru.ishop.backend.services.order;

import ru.ishop.backend.data.Id;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс заявок.
 * @author Aleksandr Smirnov.
 */
public class Order extends Id {
    /**
     * Поле id пользователя.
     */
    private long userId;
    /**
     * Поле показывает заявка завершена или нет.
     * True - заявка завершена, false - нет.
     */
    private boolean finished;
    /**
     * Поле даты заявки.
     */
    private Date date;
    /**
     * Поле цены заявки.
     */
    private double price;
    /**
     * Количество продуктов в заявке.
     */
    private int productsCount;
    /**
     * Поле отвечате за процесс.
     */
    private boolean processed;
    /**
     * Коллекция продуктов в заявке.
     */
    private final List<OrderProduct> products = new ArrayList<>();

    public boolean containsProduct(long productId) {
        for (OrderProduct orderProduct : products) {
            if (orderProduct.getProductId() == productId) {
                return true;
            }
        }
        return false;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getProductsCount() {
        return productsCount;
    }

    public void setProductsCount(int productsCount) {
        this.productsCount = productsCount;
    }

    public List<OrderProduct> getProducts() {
        return products;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }

    public void addProduct(OrderProduct orderProduct) {
        products.add(orderProduct);
    }

    public void addProducts(List<OrderProduct> orderProducts) {
        products.addAll(orderProducts);
    }
}
