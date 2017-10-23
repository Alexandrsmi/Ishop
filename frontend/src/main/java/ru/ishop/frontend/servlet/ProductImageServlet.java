package ru.ishop.frontend.servlet;

import ru.ishop.backend.context.ObjectResolver;
import ru.ishop.backend.services.Services;
import ru.ishop.frontend.util.Util;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Класс сервлета отвечает за работу с изображениями продуктов.
 * @author Aleksandr Smirnov.
 */
public class ProductImageServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png; charset=utf-8");
        req.setCharacterEncoding("UTF-8");
        long productId = Util.getLongParameter(req, "productId", -1);
        Services services = ObjectResolver.get("services");
        String path = null;
        try {
            path = services.getImageService().getProductImagePath(productId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try(OutputStream os = resp.getOutputStream()){
            Files.copy(Paths.get(path),os);
            os.flush();
        }
    }
}
