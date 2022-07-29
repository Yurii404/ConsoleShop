package ua.issoft.yurii.kupchyn.services.sorting.comparators;

import ua.issoft.yurii.kupchyn.entities.Product;

import java.lang.reflect.Field;
import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {
    private final String fieldName;
    private final String order;

    public ProductComparator(String fieldName, String order) {
        this.fieldName = fieldName;
        this.order = order;
    }

    @Override
    public int compare(Product firstProduct, Product secondProduct) {
        Comparator<Comparable> comparator = Comparable::compareTo;

        Comparable firstValue = (Comparable) getFieldValue(firstProduct, fieldName);
        Comparable secondValue = (Comparable) getFieldValue(secondProduct, fieldName);

        if (order.equals("desk")) {
            comparator = comparator.reversed();
        }
        return comparator.compare(firstValue, secondValue);

    }

    private Object getFieldValue(Product product, String fieldName) {
        try {
            Field field = product.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(product);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ProductComparatorException("Failed get value of field: " + fieldName, e);
        }
    }

}
