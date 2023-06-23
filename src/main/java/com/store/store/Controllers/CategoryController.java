package com.store.store.Controllers;

import com.store.store.Model.Entities.Bicycle;
import com.store.store.Model.Entities.Category;
import com.store.store.Model.Entities.dto.CategoryDto;
import com.store.store.Services.BicycleService;
import com.store.store.Services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    private final BicycleService bicycleService;

    private final ModelMapper modelMapper;

    public CategoryController(CategoryService categoryService, BicycleService bicycleService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.bicycleService = bicycleService;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Integer id) {
        Category category = categoryService.getCategoryById(id);
        if (category != null) {
            return ResponseEntity.ok(category);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDto category) {
        Category categoryInfo = modelMapper.map(category,Category.class);
        Category createdCategory = categoryService.createCategory(categoryInfo);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody CategoryDto categoryInfo) {
        Category category = modelMapper.map(categoryInfo,Category.class);

        if (categoryService.getCategoryById(id).getName().equalsIgnoreCase("uncategorized")) {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("this value can not be changed!");
        }
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return ResponseEntity.ok(updatedCategory);
        }
            return ResponseEntity.notFound().build();

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {

        Category category = categoryService.getCategoryById(id);
        if (category == null) {
            return ResponseEntity.notFound().build();
        }

        if (category.getName().equalsIgnoreCase("uncategorized")) {
            return ResponseEntity.badRequest().body("Cannot delete 'Uncategorized' category.");
        }

        boolean deleted = categoryService.deleteCategory(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

