package ua.issoft.yurii.kupchyn.entities;

import java.io.Serializable;
import java.util.Objects;

public class Product implements Cloneable, Serializable {
    private int id;
    private int categoryId;
    private final String producer;
    private final String name;
    private int price;

    public Product(String producer, String name, int price) {
        this.producer = producer;
        this.name = name;
        this.price = price;
    }

    public Product(int categoryId, String producer, String name, int price) {
        this.categoryId = categoryId;
        this.producer = producer;
        this.name = name;
        this.price = price;
    }

    public Product(int id, int categoryId, String producer, String name, int price) {
        this.id = id;
        this.categoryId = categoryId;
        this.producer = producer;
        this.name = name;
        this.price = price;
    }

    public String getProducer() {
        return producer;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getId() {
        return id;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return price == product.price && Objects.equals(producer, product.producer) && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(producer, name, price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "producer='" + producer + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }

    @Override
    public Product clone() {
        try {
            return (Product) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}