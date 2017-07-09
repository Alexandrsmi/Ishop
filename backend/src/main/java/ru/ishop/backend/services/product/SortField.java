package ru.ishop.backend.services.product;

/**
 * @author Aleksandr Smirnov.
 */
public enum SortField {
    no, name, price;

    public static SortField get(String value) {
        for (SortField sortField : values()) {
            if (sortField.name().equalsIgnoreCase(value)) {
                return sortField;
            }
        }
        return no;
    }
}
