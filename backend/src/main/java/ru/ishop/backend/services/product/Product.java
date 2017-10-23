package ru.ishop.backend.services.product;

import ru.ishop.backend.data.Id;

import javax.swing.*;
import java.sql.Date;
import java.sql.Timestamp;


/**
 * Класс продукта.
 * @author Aleksandr Smirnov.
 */
public class Product extends Id{
    /**
     * Поле названия товара.
     */
    private String title;
    /**
     * Поле краткого описания.
     */
    private String shortDescription;
    /**
     * Поле полного описания.
     */
    private String fullDescription;
    /**
     * Поле цены.
     */
    private double price;
    /**
     * Поле кол-ва.
     */
    private long count;
    /**
     * Поле даты создания.
     */
    private Timestamp createDatetime;
  

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public Timestamp getCreateDatetime() {
        return createDatetime;
    }

    public void setCreateDatetime(Timestamp createDatetime) {
        this.createDatetime = createDatetime;
    }
}
