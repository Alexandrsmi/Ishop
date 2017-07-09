package ru.ishop.backend.context;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Aleksandr Smirnov.
 */
public class ObjectResolver {
    private static final ApplicationContext context = new ClassPathXmlApplicationContext("spring.cfg.xml");

    public static <T> T get(String objectName) {
        return (T) context.getBean(objectName);
    }

}
