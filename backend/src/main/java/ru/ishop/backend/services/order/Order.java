package ru.ishop.backend.services.order;

import ru.ishop.backend.data.Id;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public class Order extends Id {
    private long userId;
    private boolean finished;
    private Date date;
    private double price;
    private int productsCount;
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

    public void addProduct(OrderProduct orderProduct) {
        products.add(orderProduct);
    }

    public void addProducts(List<OrderProduct> orderProducts) {
        products.addAll(orderProducts);
    }
}
