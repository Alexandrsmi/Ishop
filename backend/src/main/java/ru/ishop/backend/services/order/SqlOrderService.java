package ru.ishop.backend.services.order;

import ru.ishop.backend.services.AbstractSqlService;
import ru.ishop.backend.sql.SqlConnectionFactory;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public class SqlOrderService extends AbstractSqlService implements OrderService {

    public SqlOrderService(SqlConnectionFactory factory) {
        super(factory);
    }

    @Override
    public boolean addProduct(long productId, int productCount, long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_order_products_add(?,?,?)}");) {
            statement.setLong("_user_id", userId);
            statement.setLong("_product_id", productId);
            statement.setInt("_count", productCount);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateProductCount(long productId, int productCount, long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_order_products_update_count(?,?,?)}");) {
            statement.setLong("_user_id", userId);
            statement.setLong("_product_id", productId);
            statement.setInt("_count", productCount);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean deleteProduct(long productId, long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_order_products_delete(?,?)}");) {
            statement.setLong("_user_id", userId);
            statement.setLong("_product_id", productId);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public Order getBucket(long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_get_bucket(?)}");) {
            statement.setLong("_user_id", userId);
            Order order = new Order();
            order.setUserId(userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProductId(rs.getLong(1));
                orderProduct.setCount(rs.getInt(2));
                order.addProduct(orderProduct);
            }
            return order;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean releaseOrder(long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_finish(?)}");) {
            statement.setLong("_user_id", userId);
            if (statement.executeUpdate() > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public List<Order> historyOrders(long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_get_history(?)}");) {
            statement.setLong("_user_id", userId);
            List<Order> orders = new ArrayList<>();
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Order order = new Order();
                order.setUserId(userId);
                order.setId(rs.getLong(1));
                order.setDate(new Date(rs.getTimestamp(2).getTime()));
                order.setProductsCount(rs.getInt(3));
                order.setPrice(rs.getDouble(4));
                order.setFinished(true);
                orders.add(order);
            }
            return orders;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getBucketProductsCount(long userId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_get_bucket_products_count(?)}");) {
            statement.setLong("_user_id", userId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Order getOrder(long orderId) {
        Order order = getOrderInfo(orderId);
        List<OrderProduct> orderProducts = getOrderProducts(orderId);
        order.addProducts(orderProducts);
        return order;
    }



    private Order getOrderInfo(long orderId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_get_info(?)}");) {
            statement.setLong("_order_id", orderId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Order order = new Order();
                order.setId(rs.getLong(1));
                order.setDate(new Date(rs.getTimestamp(2).getTime()));
                order.setProductsCount(rs.getInt(3));
                order.setPrice(rs.getDouble(4));
                order.setUserId(rs.getLong(5));
                order.setFinished(rs.getBoolean(6));
                return order;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private List<OrderProduct> getOrderProducts(long orderId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_orders_get(?)}");) {
            statement.setLong("_order_id", orderId);
            ResultSet rs = statement.executeQuery();
            List<OrderProduct> orderProducts = new ArrayList<>();
            while (rs.next()) {
                OrderProduct orderProduct = new OrderProduct();
                orderProduct.setProductId(rs.getLong(1));
                orderProduct.setCount(rs.getInt(2));
                orderProduct.setOrderId(rs.getLong(3));
                orderProducts.add(orderProduct);
            }
            return orderProducts;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}