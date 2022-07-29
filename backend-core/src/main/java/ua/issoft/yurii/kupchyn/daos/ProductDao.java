package ua.issoft.yurii.kupchyn.daos;

import ua.issoft.yurii.kupchyn.entities.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    List<Product> getAllByCategoryId(int categoryId);

    void add(int categoryId, Product product);

    Optional<Product> getById(int productId);
}
