package ru.ishop.frontend.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import ru.ishop.backend.context.ObjectResolver;
import ru.ishop.backend.services.Services;
import ru.ishop.backend.services.product.Product;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;

/**
 * @author Aleksandr Smirnov.
 */
public class UploadProductInfo {

    public static UploadProductInfoResult uploadProduct(HttpServletRequest req) {
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(5_000_000);
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setSizeMax(5_000_000);
        UploadProductInfoResult result = new UploadProductInfoResult();
        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            if (createProduct(fileItems, result)) {
                FileItem image = getFileItem("image", fileItems);
                uploadImage(image, result.getProduct().getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setError(true);
        }
        return result;
    }

    private static FileItem getFileItem(String name, List<FileItem> fileItemsList) {
        for (FileItem fileItem : fileItemsList) {
            if (fileItem.getFieldName().equals(name)) {
                return fileItem;
            }
        }
        return null;
    }

    private static String getFileItemValue(String name, List<FileItem> fileItemsList) {
        FileItem fileItem = getFileItem(name, fileItemsList);
        if (fileItem != null) {
            return fileItem.getString();
        }else {
            return null;
        }
    }

    private static void uploadImage(FileItem fileItem, long productId) throws Exception {
        Services services = ObjectResolver.get("services");
        String path = services.getImageService().getProductImagePath(productId);
        fileItem.write(new File(path));
    }

    private static boolean createProduct(List<FileItem> fileItems, UploadProductInfoResult result) {
        Product product = new Product();
        result.setProduct(product);
        product.setTitle(getFileItemValue("title", fileItems));
        product.setShortDescription(getFileItemValue("shortDescription", fileItems));
        product.setFullDescription(getFileItemValue("fullDescription", fileItems));
        product.setPrice(Util.getDoubleParameter(getFileItem("price", fileItems), -1));
        if (Util.isBlank(product.getTitle())) {
            result.setTitleError("Title must be filled");
        }
        if (Util.isBlank(product.getShortDescription())){
            result.setShortDescriptionError("Short description must be filled");
        }
        if (Util.isBlank(product.getFullDescription())) {
            result.setFullDescriptionError("Full description must be filled");
        }
        if (product.getPrice() == -1) {
            result.setPriceError("Price must be filled");
        }
        if (!result.isError()) {
            Services services = ObjectResolver.get("services");
            result.setError(!services.getProductService().createProduct(product));
        }
        return !result.isError();
    }


    public static class UploadProductInfoResult {
        private Product product;
        private boolean error;
        private String titleError = null;
        private String shortDescriptionError = null;
        private String fullDescriptionError = null;
        private String priceError = null;

        public Product getProduct() {
            return product;
        }

        public void setProduct(Product product) {
            this.product = product;
        }

        public boolean isError() {
            return error;
        }

        public void setError(boolean error) {
            this.error = error;
        }

        public String getTitleError() {
            return titleError;
        }

        public void setTitleError(String titleError) {
            this.titleError = titleError;
            error = true;
        }

        public String getShortDescriptionError() {
            return shortDescriptionError;
        }

        public void setShortDescriptionError(String shortDescriptionError) {
            this.shortDescriptionError = shortDescriptionError;
            error = true;
        }

        public String getFullDescriptionError() {
            return fullDescriptionError;
        }

        public void setFullDescriptionError(String fullDescriptionError) {
            this.fullDescriptionError = fullDescriptionError;
            error = true;
        }

        public String getPriceError() {
            return priceError;
        }

        public void setPriceError(String priceError) {
            this.priceError = priceError;
            error = true;
        }
    }
}
