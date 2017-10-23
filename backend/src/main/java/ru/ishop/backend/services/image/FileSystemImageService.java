package ru.ishop.backend.services.image;

import java.io.InputStream;

/**
 * Класс отвечает за получение изображеия.
 * @author Aleksandr Smirnov.
 */
public class FileSystemImageService implements ImageService {
    /**
     * Поле пути к изображению.
     */
    private String productImageRootPath;

    /**
     * Метод получения пути изображенеия продукта.
     * @param productId - id продукта.
     * @return - возвращаем путь изображения.
     * @throws Exception
     */
    @Override
    public String getProductImagePath(long productId) throws Exception {
        pathImage();
        return String.format("%s%s%s",productImageRootPath,productId,".png");
    }

    /**
     * Метод достает путь к папкам где храняться изображения.
     * @throws Exception
     */
    private void pathImage() throws Exception {
        Setting setting = new Setting();
        ClassLoader classLoader = Setting.class.getClassLoader();
        try (InputStream io = classLoader.getResourceAsStream("app.properties")) {
            setting.load(io);
        }
        String productPath = setting.getValue("products.path");
        productImageRootPath = new String(productPath);
    }
}
