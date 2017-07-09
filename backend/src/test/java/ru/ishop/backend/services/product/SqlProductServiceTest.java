package ru.ishop.backend.services.product;

import org.junit.Test;
import ru.ishop.backend.services.AbstractSqlServiceTest;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

/**
 * @author Aleksandr Smirnov.
 */
public class SqlProductServiceTest extends AbstractSqlServiceTest {

    @Test
    public void createProduct() {
        long beforeCreateTime = (System.currentTimeMillis() / 1000) * 1000;
        Product product = createNewProduct();
        long afterCreateTime = System.currentTimeMillis();
        Product dbProduct = getProductService().getProduct(product.getId());
        assertNotNull(dbProduct);
        assertEquals(product.getTitle(), dbProduct.getTitle());
        assertEquals(product.getShortDescription(), dbProduct.getShortDescription());
        assertEquals(product.getFullDescription(), dbProduct.getFullDescription());
        assertEquals(product.getPrice(), dbProduct.getPrice(), 0.000001);
        assertEquals(product.getCount(), dbProduct.getCount());
        assertTrue(dbProduct.getCreateDatetime().getTime() >= beforeCreateTime);
        assertTrue(dbProduct.getCreateDatetime().getTime() <= afterCreateTime);
    }

    @Test
    public void updateProduct() {
        Product product = createNewProduct();
        Product dbProduct = getProductService().getProduct(product.getId());
        product.setTitle("upd-" + product.getTitle());
        product.setShortDescription("upd-" + product.getShortDescription());
        product.setFullDescription("upd-" + product.getFullDescription());
        product.setPrice(1 + product.getPrice());
        product.setCount(1 + product.getCount());
        product.setCreateDatetime(new Timestamp(0));
        boolean result = getProductService().updateProduct(product);
        assertTrue(result);
        Product dbProductAfterUpdate = getProductService().getProduct(product.getId());
        assertNotNull(dbProductAfterUpdate);
        assertEquals(product.getTitle(), dbProductAfterUpdate.getTitle());
        assertEquals(product.getShortDescription(), dbProductAfterUpdate.getShortDescription());
        assertEquals(product.getFullDescription(), dbProductAfterUpdate.getFullDescription());
        assertEquals(product.getPrice(), dbProductAfterUpdate.getPrice(), 0.000001);
        assertEquals(product.getCount(), dbProductAfterUpdate.getCount());
        assertEquals(dbProduct.getCreateDatetime(), dbProductAfterUpdate.getCreateDatetime());
    }

    @Test
    public void deleteProduct() {
        Product product = createNewProduct();
        boolean result = getProductService().deleteProduct(product.getId());
        assertTrue(result);
        Product dbProduct = getProductService().getProduct(product.getId());
        assertNull(dbProduct);
    }

    @Test
    public void deleteNotExistingProduct() {
        Product product = createNewProduct();
        boolean result = getProductService().deleteProduct(product.getId() + 1);
        assertFalse(result);
    }

    @Test
    public void getNotExistingProductById() {
        Product product = createNewProduct();
        Product dbProduct = getProductService().getProduct(product.getId() + 1);
        assertNull(dbProduct);
    }

    @Test
    public void getProducts_NoSort() {
        for (int i = 0; i <= 5; i++) {
            createNewProduct();
        }
        List<Product> list = getProductService().getProducts(0, 3, SortField.no);
        assertEquals(3, list.size());
        List<Product> list2 = getProductService().getProducts(2, 4, SortField.no);
        assertEquals(4, list2.size());
        assertEquals(list.get(2), list2.get(0));
    }

    @Test
    public void getProducts_SortByName() {
        for (int i = 0; i <= 2; i++) {
            createNewProduct();
        }
        List<Product> productsSortedByName = getProductService().getProducts(0, 3, SortField.name);
        assertEquals(3, productsSortedByName.size());
        List<String> names = productsSortedByName.stream().
                map(Product::getTitle).
                collect(Collectors.toList());
        List<String> expectedNames = new ArrayList<>(names);
        expectedNames.sort(String::compareToIgnoreCase);
        assertEquals(expectedNames, names);
    }

    @Test
    public void getProducts_SortByPrice() {
        for (int i = 0; i <= 2; i++) {
            createNewProduct();
        }
        List<Product> productsSortedByName = getProductService().getProducts(0, 3, SortField.price);
        assertEquals(3, productsSortedByName.size());
        List<Double> prices = productsSortedByName.stream().
                map(Product::getPrice).
                collect(Collectors.toList());
        List<Double> expectedNames = new ArrayList<>(prices);
        expectedNames.sort(Double::compareTo);
        assertEquals(expectedNames, prices);
    }

    @Test
    public void getProductsCount() {
        Product product = createNewProduct();
        List<Product> list = getProductService().getProducts(0, product.getCount(), SortField.no);
        long count = list.size();
        long productCount = getProductService().getProductsCount();
        assertTrue(count == productCount);
//        getProductService().deleteProduct(product.getId());
    }

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
}
