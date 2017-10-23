package ru.ishop.backend.services.image;

import java.io.InputStream;
import java.util.Properties;

/**
 * Класс 
 * @author Aleksandr Smirnov.
 */
public class Setting {
    private final Properties prs = new Properties();

    public void load(InputStream io) {
        try {
            this.prs.load(io);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getValue(String key) {
        return this.prs.getProperty(key);
    }
}

