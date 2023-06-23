package com.store.store.Services;

import com.store.store.Model.Entities.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {

    List<Category> getAllCategories();

    Category getCategoryById(int id);

    Optional<Category> getCategoryByName(String name);

    Category saveCategory(Category category);

    Boolean deleteCategory(int id);

    Category createCategory(Category category);

    Category updateCategory(Integer id, Category category);


}
