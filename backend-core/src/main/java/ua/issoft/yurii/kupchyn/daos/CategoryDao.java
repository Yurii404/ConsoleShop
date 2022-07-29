package ua.issoft.yurii.kupchyn.daos;

import ua.issoft.yurii.kupchyn.entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryDao {
    List<Category> getAll();

    Optional<Category> getByName(String categoryName);

    Optional<Category> getById(int categoryId);

}
