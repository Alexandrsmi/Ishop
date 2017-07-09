package ru.ishop.backend.services.product;

import ru.ishop.backend.services.AbstractSqlService;
import ru.ishop.backend.services.user.User;
import ru.ishop.backend.services.user.UserState;
import ru.ishop.backend.sql.SqlConnectionFactory;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public class SqlProductService extends AbstractSqlService implements ProductService {


    public SqlProductService(SqlConnectionFactory factory) {
        super(factory);
    }

    @Override
    public boolean createProduct(Product product) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_products_create(?,?,?,?,?,?)}");) {
            statement.setString("_title", product.getTitle());
            statement.setString("_short_description", product.getShortDescription());
            statement.setString("_full_description", product.getFullDescription());
            statement.setDouble("_price", product.getPrice());
            statement.setLong("_count", product.getCount());
            statement.registerOutParameter("_product_id", Types.BIGINT);
            if (statement.executeUpdate() > 0) {
                product.setId(statement.getLong("_product_id"));
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public boolean updateProduct(Product product) {
        try (Connection connection = getConnection()) {
            try (CallableStatement statement = connection.prepareCall("{call sp_products_update(?,?,?,?,?,?)}")) {
                statement.setLong("_product_id", product.getId());
                statement.setString("_title", product.getTitle());
                statement.setString("_short_description", product.getShortDescription());
                statement.setString("_full_description", product.getFullDescription());
                statement.setDouble("_price", product.getPrice());
                statement.setLong("_count", product.getCount());
                if (statement.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }


    @Override
    public boolean deleteProduct(long productId) {
        try (Connection connection = getConnection()) {
            try (CallableStatement statement = connection.prepareCall("{call sp_products_delete(?)}")) {
                statement.setLong("_product_id", productId);
                File file = new File("C:\\Users\\Александр\\Desktop\\Новая папка\\ishop\\ishop-images\\products\\"+productId+".png");
                file.delete();
                if (statement.executeUpdate() > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    @Override
    public Product getProduct(long productId) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_products_get_by_id(?)}");) {
            statement.setLong("_product_id", productId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong(1));
                product.setTitle(rs.getString(2));
                product.setShortDescription(rs.getString(3));
                product.setFullDescription(rs.getString(4));
                product.setPrice(rs.getDouble(5));
                product.setCount(rs.getLong(6));
                product.setCreateDatetime(rs.getTimestamp(7));
                return product;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public List<Product> getProducts(long offset, long maxCount,SortField sortField) {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_products_list(?,?,?)}");) {
            statement.setLong("_offset", offset);
            statement.setLong("_max_count", maxCount);
            statement.setString("_sort_field", sortField.name());
            ResultSet rs = statement.executeQuery();
            List<Product> list = new ArrayList<>();
            while (rs.next()) {
                Product product = new Product();
                product.setId(rs.getLong(1));
                product.setTitle(rs.getString(2));
                product.setShortDescription(rs.getString(3));
                product.setFullDescription(rs.getString(4));
                product.setPrice(rs.getDouble(5));
                product.setCount(rs.getLong(6));
                product.setCreateDatetime(rs.getTimestamp(7));
                list.add(product);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public long getProductsCount() {
        try (Connection connection = getConnection();
             CallableStatement statement = connection.prepareCall("{call sp_products_count()}");) {
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                return rs.getLong(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
