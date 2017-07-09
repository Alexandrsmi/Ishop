package ru.ishop.backend.services.image;

/**
 * @author Aleksandr Smirnov.
 */
public class FileSystemImageService implements ImageService {
    private final static String IMAGE_ROOT_PATH = "C:\\Users\\Александр\\Desktop\\Новая папка\\ishop\\ishop-images";
    private final static String PRODUCT_IMAGE_ROOT_PATH = IMAGE_ROOT_PATH + "\\products\\";

    @Override
    public String getProductImagePath(long productId) {
        return PRODUCT_IMAGE_ROOT_PATH+productId+".png";
    }
}
