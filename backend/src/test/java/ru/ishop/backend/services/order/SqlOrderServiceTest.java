package ru.ishop.backend.services.order;

import com.sun.org.apache.xpath.internal.operations.Or;
import org.junit.Test;
import ru.ishop.backend.services.AbstractSqlServiceTest;
import ru.ishop.backend.services.product.Product;
import ru.ishop.backend.services.user.User;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Aleksandr Smirnov.
 */
public class SqlOrderServiceTest extends AbstractSqlServiceTest {

    @Test
    public void addProduct() {
        Product product1 = createNewProduct();
        Product product2 = createNewProduct();
        User user = createNewUser();
        assertTrue(getOrderService().addProduct(product1.getId(), 5, user.getId()));
        assertTrue(getOrderService().addProduct(product2.getId(), 8, user.getId()));
        Order order = getOrderService().getBucket(user.getId());
        assertEquals(user.getId().longValue(), order.getUserId());
        assertFalse(order.isFinished());
        assertEquals(2, order.getProducts().size());
        OrderProduct dbProduct = order.getProducts().get(0);
        assertEquals(product1.getId().longValue(), dbProduct.getProductId());
        assertEquals(5, dbProduct.getCount());
        dbProduct = order.getProducts().get(1);
        assertEquals(product2.getId().longValue(), dbProduct.getProductId());
        assertEquals(8, dbProduct.getCount());
    }

    @Test
    public void updateProductCount() {
        Product product = createNewProduct();
        User user = createNewUser();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        getOrderService().updateProductCount(product.getId(), 8, user.getId());
        Order order = getOrderService().getBucket(user.getId());
        assertEquals(1, order.getProducts().size());
        OrderProduct dbProduct = order.getProducts().get(0);
        assertEquals(8, dbProduct.getCount());
    }

    @Test
    public void deleteProduct_oneProductInBucket() {
        Product product = createNewProduct();
        User user = createNewUser();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        getOrderService().deleteProduct(product.getId(), user.getId());
        Order order = getOrderService().getBucket(user.getId());
        assertTrue(order.getProducts().isEmpty());
    }

    @Test
    public void deleteProduct_manyProductsInBucket() {
        Product product1 = createNewProduct();
        Product product2 = createNewProduct();
        User user = createNewUser();
        getOrderService().addProduct(product1.getId(), 5, user.getId());
        getOrderService().addProduct(product2.getId(), 19, user.getId());
        getOrderService().deleteProduct(product1.getId(), user.getId());
        Order order = getOrderService().getBucket(user.getId());
        assertEquals(1, order.getProducts().size());
        assertEquals(product2.getId().longValue(), order.getProducts().get(0).getProductId());
    }

    @Test
    public void getBucket() {
        User user = createNewUser();
        Order order = getOrderService().getBucket(user.getId());
        assertEquals(user.getId().longValue(), order.getUserId());
        assertTrue(order.getProducts().isEmpty());
        Product product = createNewProduct();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        order = getOrderService().getBucket(user.getId());
        assertEquals(1, order.getProducts().size());
        Product product1 = createNewProduct();
        getOrderService().addProduct(product1.getId(), 6, user.getId());
        order = getOrderService().getBucket(user.getId());
        assertEquals(2, order.getProducts().size());
        OrderProduct dbProduct = order.getProducts().get(0);
        assertEquals(product.getId().longValue(), dbProduct.getProductId());
        assertEquals(5, dbProduct.getCount());
        dbProduct = order.getProducts().get(1);
        assertEquals(product1.getId().longValue(), dbProduct.getProductId());
        assertEquals(6, dbProduct.getCount());
    }

    /**
     * Тестируем метод реализции заявки покупателем.
     */

    @Test
    public void releaseOrder() {
        User user = createNewUser();
        Product product = createNewProduct();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        Product product1 = createNewProduct();
        getOrderService().addProduct(product1.getId(), 10, user.getId());
        getOrderService().releaseOrder(user.getId());
        List<Order> orders = getOrderService().historyOrders(user.getId());
        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(user.getId().longValue(), order.getUserId());
        assertTrue(order.isFinished());
    }

    /**
     * Тест метод получения истории заказов пользователя.
     */
    @Test
    public void getHistoryOrders() {
        User user = createNewUser();
        List<Order> orders = getOrderService().historyOrders(user.getId());
        assertEquals(0, getOrderService().historyOrders(user.getId()).size());
        Product product = createNewProduct();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        getOrderService().releaseOrder(user.getId());
        assertEquals(1, getOrderService().historyOrders(user.getId()).size());
        Product product1 = createNewProduct();
        getOrderService().addProduct(product1.getId(), 10, user.getId());
        getOrderService().releaseOrder(user.getId());
        assertEquals(2, getOrderService().historyOrders(user.getId()).size());
    }

    @Test
    public void getBucketProductsCount() {
        User user = createNewUser();
        Product product = createNewProduct();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        Product product1 = createNewProduct();
        getOrderService().addProduct(product1.getId(), 10, user.getId());
        int count = getOrderService().getBucketProductsCount(user.getId());
        assertEquals(2, count);
    }

    @Test
    public void getOrderInfo() {
        User user = createNewUser();
        long beforeTime = System.currentTimeMillis() - 1000;
        Product product = createNewProduct();
        Product product1 = createNewProduct();
        getOrderService().addProduct(product.getId(), 5, user.getId());
        getOrderService().addProduct(product1.getId(), 10, user.getId());
        getOrderService().releaseOrder(user.getId());
        long afterTime = System.currentTimeMillis();
        List<Order> orders = getOrderService().historyOrders(user.getId());
        assertEquals(1, orders.size());
        Order order = getOrderService().getOrder(orders.get(0).getId());
        assertEquals(user.getId().longValue(), order.getUserId());
        assertEquals(((product.getPrice() * 5) + (product1.getPrice() * 10)), order.getPrice(), 0.001);
        assertEquals(2, order.getProductsCount());
        assertTrue(order.isFinished());
        assertTrue(beforeTime <= order.getDate().getTime());
        assertTrue(afterTime >= order.getDate().getTime());
        List<OrderProduct> orderProducts = order.getProducts();
        assertEquals(2, orderProducts.size());
        assertEquals(product.getId().longValue(), orderProducts.get(0).getProductId());
        assertEquals(5, orderProducts.get(0).getCount());
        assertEquals(order.getId().longValue(), orderProducts.get(0).getOrderId());
        assertEquals(product1.getId().longValue(), orderProducts.get(1).getProductId());
        assertEquals(10, orderProducts.get(1).getCount());
        assertEquals(order.getId().longValue(), orderProducts.get(1).getOrderId());
    }


    @Test
    public void getOrdersAllUsers(){

        List<Order> orders = getOrderService().getOrdersAllUsers();
        assertTrue(!orders.isEmpty());
    }

    /**
     * Метод создаёт новый товар.
     * @return product - товар.
     */
    private Product createNewProduct() {
        Product product = new Product();
        product.setTitle(salted("phone"));
        product.setShortDescription(salted("smartPhone"));
        product.setFullDescription("full description");
        product.setCount((int) (Math.random() * 1000));
        product.setPrice(Math.random() * 1000);
        boolean result = getProductService().createProduct(product);
        assertTrue(result);
        assertNotNull(product.getId());
        return product;
    }

    /**
     * Метод создаёт покупателя.
     * @return user - покупателя.
     */
    private User createNewUser() {
        User user = new User();
        user.setFirstName("Vasya");
        user.setLastName("Petrov");
        user.setEmail(salted("Petr@email.ru"));
        user.setPassword("123");
        boolean result = getUserService().createUser(user);
        assertTrue(result);
        assertNotNull(user.getId());
        return user;
    }
}