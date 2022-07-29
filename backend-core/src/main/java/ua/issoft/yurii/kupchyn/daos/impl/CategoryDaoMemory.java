package ua.issoft.yurii.kupchyn.daos.impl;

import ua.issoft.yurii.kupchyn.daos.CategoryDao;
import ua.issoft.yurii.kupchyn.daos.CategoryLoader;
import ua.issoft.yurii.kupchyn.daos.CategoryLoaderException;
import ua.issoft.yurii.kupchyn.daos.DaoException;
import ua.issoft.yurii.kupchyn.entities.Category;

import java.util.List;
import java.util.Optional;

public class CategoryDaoMemory implements CategoryDao {
    private final List<Category> categories;

    public CategoryDaoMemory() {
        CategoryLoader categoryLoader = new CategoryLoader();

        try {
            categories = categoryLoader.load("ua.issoft.yurii.kupchyn.entities.categories");
        } catch (CategoryLoaderException e) {
            throw new DaoException("Failed load category from package", e);
        }
    }

    public List<Category> getAll() {
        return categories;
    }

    public Optional<Category> getByName(String categoryName) {
        return categories.stream()
                .filter(item -> item.getName().equals(categoryName)).findAny();
    }

    @Override
    public Optional<Category> getById(int categoryId) {
        return categories.stream()
                .filter(item -> item.getId() == categoryId).findAny();
    }

}
