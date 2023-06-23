package com.store.store.Services.Impl;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Category;

import com.store.store.Model.Repositories.BicycleRepository;
import com.store.store.Model.Repositories.CategoryRepository;
import com.store.store.Services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final BicycleRepository bicycleRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository,BicycleRepository bicycleRepository) {
        this.categoryRepository = categoryRepository;
        this.bicycleRepository = bicycleRepository;
    }



    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Boolean deleteCategory(int id) {
        if (categoryRepository.existsById(id)) {

            Category defaultCategory = categoryRepository.findByName("uncategorized").get();

            for (Bicycle bicycle : categoryRepository.findById(id).get().getBicycles()) {
                bicycle.setCategory(defaultCategory);
                bicycleRepository.save(bicycle);
            }

            categoryRepository.deleteById(id);
            return true; // Deletion successful
        } else {
            return false; // Category with the given ID does not exist
        }
    }
    @Override
    public Category createCategory(Category category) {
        // Perform any necessary validation or business logic checks
        // For example, you can check if the category already exists

        // Call the CategoryRepository to save the category
        return categoryRepository.save(category);
    }
    @Override
    public Category updateCategory(Integer id, Category category) {
        // Check if the category with the given id exists in the database
        Category existingCategory = categoryRepository.findById(id).orElse(null);
        if (existingCategory == null) {
            return null; // Category not found, return null or throw an exception
        }

        // Update the existing category with the new data
        existingCategory.setName(category.getName());

        // Save the updated category
        return categoryRepository.save(existingCategory);
    }

}
