package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.daos.ProductDao;
import ua.issoft.yurii.kupchyn.entities.Product;

import java.util.Optional;

public class ProductService {
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProductInCategory(int categoryId, Product product) {
        productDao.add(categoryId, product);
    }

    public Optional<Product> getById(int productId) {
        return productDao.getById(productId);
    }
}
