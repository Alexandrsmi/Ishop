package ru.ishop.backend.services.product;

import java.util.List;

/**
 * Интерфейс продукта.
 * @author Aleksandr Smirnov.
 */
public interface ProductService {
    /**
     * Метод создания продукта.
     * @param product - продукт.
     * @return - true при успешном создании.
     */
    boolean createProduct(Product product);

    /**
     * Метод изменения продукта.
     * @param product - продукт.
     * @return - true при успешном изменении.
     */
    boolean updateProduct(Product product);

    /**
     * Метод удаления продукта.
     * @param productId - id продукта.
     * @return - true при успешном удалении.
     */
    boolean deleteProduct(long productId);

    /**
     * Метод получения продукта по id/
     * @param productId - id продукта.
     * @return - продукт.
     */
    Product getProduct(long productId);

    /**
     * Метод получения списка продуктов.
     * @param offset - индекс товара.
     * @param maxCount - кол-во товаров.
     * @param sortField - значение сортировки.
     * @return - список товаров.
     */
    List<Product> getProducts(long offset, long maxCount,SortField sortField);

    /**
     * Метод выдает кол-во товаров.
     * @return - кол-во товаров.
     */
    long getProductsCount();
}
