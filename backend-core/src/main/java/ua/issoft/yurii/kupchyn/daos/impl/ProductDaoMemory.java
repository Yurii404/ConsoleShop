package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.ProductDao;
import ua.issoft.yurii.kupchyn.entities.Category;
import ua.issoft.yurii.kupchyn.entities.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDaoMemory implements ProductDao {
    CategoryDaoMemory categoryDaoMemory;

    public ProductDaoMemory(CategoryDaoMemory categoryDaoMemory) {
        this.categoryDaoMemory = categoryDaoMemory;
    }

    public List<Product> getAllByCategoryId(int categoryId) {
        List<Category> categoryList = categoryDaoMemory.getAll();
        List<Product> resultListOfProduct = new ArrayList<>();
        for (Category category : categoryList) {
            if (category.getId() == categoryId) {
                resultListOfProduct = category.getProducts();

            }
        }
        return resultListOfProduct;
    }

    public void add(int categoryId, Product product) {
        List<Category> categoryList = categoryDaoMemory.getAll();

        for (Category category : categoryList) {
            if (category.getId() == categoryId) {
                category.addProduct(product);
            }
        }
    }

    @Override
    public Optional<Product> getById(int productId) {
        return categoryDaoMemory.getAll().stream()
                .flatMap(category -> category.getProducts().stream())
                .filter(product -> product.getId() == productId)
                .findAny();
    }
}