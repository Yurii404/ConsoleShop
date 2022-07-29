package ua.issoft.yurii.kupchyn.services;

import ua.issoft.yurii.kupchyn.daos.CategoryDao;
import ua.issoft.yurii.kupchyn.entities.Category;

import java.util.List;
import java.util.Optional;


public class CategoryService {
    CategoryDao categoryDao;

    public CategoryService(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    public List<Category> getAllCategories() {
        return categoryDao.getAll();
    }

    public Optional<Category> getByName(String name) {
        return categoryDao.getByName(name);
    }

    public Optional<Category> getById(int categoryId) {
        return categoryDao.getById(categoryId);
    }
}

