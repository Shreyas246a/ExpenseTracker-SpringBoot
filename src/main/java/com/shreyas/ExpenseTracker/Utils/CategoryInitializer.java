package com.shreyas.ExpenseTracker.Utils;

import com.shreyas.ExpenseTracker.entity.Category;
import com.shreyas.ExpenseTracker.repository.CategoryRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CategoryInitializer {
    @Autowired
    private final CategoryRepository categoryRepository;

    @PostConstruct
    public void init() {
        for (String cat : DefaultCategories.CATEGORIES) {
            if (!categoryRepository.existsByNameAndUserIsNull(cat)) {
                categoryRepository.save(
                    Category.builder()
                        .name(cat)
                        .isDefault(true)
                        .build()
                );
            }
        }
    }
}
