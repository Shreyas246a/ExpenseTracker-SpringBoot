package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.DTO.Response.CategoryDTO;
import com.shreyas.ExpenseTracker.entity.Category;

import java.util.List;

public interface CategoryService {
    public List<CategoryDTO> getAllCategoriesByUser();
    public CategoryDTO getCategoryById(Long id);
    public CategoryDTO createCategory(CategoryDTO dto);
    public  void deleteCategoryById(Long id);
    public CategoryDTO updateCategory(Long id, CategoryDTO dto);
}
