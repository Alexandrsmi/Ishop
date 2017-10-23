package ru.ishop.backend.services.product;

/**
 * Класс сортировки.
 * @author Aleksandr Smirnov.
 */
public enum SortField {
    /**
     * no - без сортировки.
     * name - сортировка по имени.
     * price - сортировка по цене.
     */
    no, name, price;

    /**
     * Метод определяет еобходимую сортировку.
     * @param value значение сортировки.
     * @return - enum значение сортировки.
     */
    public static SortField get(String value) {
        for (SortField sortField : values()) {
            if (sortField.name().equalsIgnoreCase(value)) {
                return sortField;
            }
        }
        return no;
    }
}
