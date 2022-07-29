package ua.issoft.yurii.kupchyn.services.sorting;

import java.util.Objects;

public class SortCriteria {
    private String fieldName;
    private String order;

    public SortCriteria(String name, String order) {
        this.fieldName = name;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SortCriteria that = (SortCriteria) o;
        return Objects.equals(fieldName, that.fieldName) && Objects.equals(order, that.order);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName, order);
    }
}
