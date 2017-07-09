package ru.ishop.backend.services.product;

import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public interface ProductService {
    boolean createProduct(Product product);
    boolean updateProduct(Product product);
    boolean deleteProduct(long productId);
    Product getProduct(long productId);
    List<Product> getProducts(long offset, long maxCount,SortField sortField);
    long getProductsCount();

}
