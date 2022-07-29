package ua.issoft.yurii.kupchyn.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Catalog implements Serializable {

    private static final String NAME = "Catalog";
    private final List<Category> categories = new ArrayList<>();

    public void addCategory(Category category) {

        categories.add(category);

    }

    public List<Category> getCategories() {
        return categories;
    }

    public String getName() {
        return NAME;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Catalog catalog = (Catalog) o;
        return Objects.equals(categories, catalog.categories);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categories);
    }

    @Override
    public String toString() {
        return "Catalog{" +
                "categories=" + categories +
                '}';
    }
}
