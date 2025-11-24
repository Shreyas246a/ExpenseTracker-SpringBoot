package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Response.CategoryDTO;
import com.shreyas.ExpenseTracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;
    @GetMapping("/allCategories")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategoriesByUser();
        return ResponseEntity.ok(categories);
    }
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(Long id) {
        CategoryDTO category = categoryService.getCategoryById(id);
        return ResponseEntity.ok(category);
    }
    @PostMapping("/create")
    public ResponseEntity<CategoryDTO> createCategory(@RequestBody CategoryDTO dto) {
        CategoryDTO category = categoryService.createCategory(dto);
        return ResponseEntity.ok(category);
    }
    @PutMapping("/{id}/update")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        CategoryDTO category = categoryService.updateCategory(id, dto);
        return ResponseEntity.ok(category);
    }
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.noContent().build();
    }
}
