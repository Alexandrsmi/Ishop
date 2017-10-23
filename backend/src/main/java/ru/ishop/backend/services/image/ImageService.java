package ru.ishop.backend.services.image;

/**
 * Интерфейс работы с изображениями.
 * @author Aleksandr Smirnov.
 */
public interface ImageService {

    String getProductImagePath(long productId) throws Exception;
}
