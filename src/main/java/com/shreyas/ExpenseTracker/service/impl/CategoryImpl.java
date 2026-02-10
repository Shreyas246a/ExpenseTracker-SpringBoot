package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.Response.CategoryDTO;
import com.shreyas.ExpenseTracker.Utils.AuthUtil;
import com.shreyas.ExpenseTracker.entity.Category;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.CategoryRepository;
import com.shreyas.ExpenseTracker.repository.ExpenseRepository;
import com.shreyas.ExpenseTracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryImpl implements CategoryService {
    private final AuthUtil authUtil;
    private final CategoryRepository categoryRepository;
    private final ExpenseRepository expenseRepository;

    public CategoryImpl(AuthUtil authUtil, CategoryRepository categoryRepository, ExpenseRepository expenseRepository) {
        this.authUtil = authUtil;
        this.categoryRepository = categoryRepository;
        this.expenseRepository = expenseRepository;
    }

    @Override
    public List<CategoryDTO> getAllCategoriesByUser() {
        User user = authUtil.getCurrentUser();
            List<Category> categories = categoryRepository.findByUserId(user.getId()).orElseThrow();
            return categories.stream().map(category -> {
                return   CategoryDTO.builder()
                        .id(category.getId())
                        .isDefault(category.getIsDefault())
                        .name(category.getName())
                        .build();
            }).toList();
    }
    @Override
    public CategoryDTO getCategoryById(Long id) {
        User user = authUtil.getCurrentUser();
            Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
            if(category.getUser().getId().equals(user.getId())){
                return   CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build();
            }else {
                throw new RuntimeException("Access Denied");
            }
        }


    @Override
    public CategoryDTO createCategory(CategoryDTO dto) {
        User user = authUtil.getCurrentUser();
         Category category = Category.builder().name(dto.getName())
                 .isDefault(false).user(user).build();
         category = categoryRepository.save(category);
            return   CategoryDTO.builder()
                    .id(category.getId())
                    .isDefault(category.getIsDefault())
                    .name(category.getName())
                    .build();
    }

    @Override
    public void deleteCategoryById(Long id) {
        User user = authUtil.getCurrentUser();
            Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
            if(category.getUser().getId().equals(user.getId())){
                System.out.println(expenseRepository.existsByCategory_Id(category.getId()));
                if(expenseRepository.existsByCategory_Id(category.getId())){
                    throw new RuntimeException("Cannot delete category with associated expenses");
                }else {
                    categoryRepository.deleteById(id);
                }
            }else {
                throw new RuntimeException("Access Denied");
            }
    }

    @Override
    public CategoryDTO updateCategory(Long id, CategoryDTO dto) {
        User user = authUtil.getCurrentUser();
            Category category= categoryRepository.findById(id).orElseThrow(()->new RuntimeException("Category not found"));
            if(category.getIsDefault()){
                throw new RuntimeException("Cannot update default category");
            }
            if(category.getUser().getId().equals(user.getId())){
                category.setName(dto.getName());
                category = categoryRepository.save(category);
                return   CategoryDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .isDefault(category.getIsDefault())
                        .build();
            }else {
                throw new RuntimeException("Access Denied");
            }
    }


}
