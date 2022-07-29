package ua.issoft.yurii.kupchyn.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Category implements Cloneable, Serializable {
    private int id;
    private String name;
    private List<Product> products;

    public Category() {
        products = new ArrayList<>();
    }

    public Category(int id, String name, List<Product> products) {
        this.id = id;
        this.name = name;
        this.products = products;
    }

    public int getId() {
        return id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public String getName() {
        return name;
    }

    public boolean addProduct(Product product) {
        return products.add(product);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(products, category.products) && Objects.equals(getName(), category.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(products, getName());
    }

    @Override
    public String toString() {
        return getName() + "{" +
                "NAME='" + getName() + '\'' +
                ", products=" + products +
                '}';
    }

    @Override
    public Category clone() throws CategoryCloneException {
        Category clone;

        try {
            clone = (Category) super.clone();

            clone.products = new ArrayList<>(products.size());
            products.forEach(product -> clone.products.add(product.clone()));

        } catch (CloneNotSupportedException e) {
            throw new CategoryCloneException("Failed to clone category", e);
        }
        return clone;
    }
}
