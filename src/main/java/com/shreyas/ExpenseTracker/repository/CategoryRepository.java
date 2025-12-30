package com.shreyas.ExpenseTracker.repository;

import com.shreyas.ExpenseTracker.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    Optional<List<Category>> findByUserId(Long userId);
    Optional<Category> findByName(String name);
    Optional<Category> findByNameAndUserId(String name, Long userId);
    Optional<Category> findByNameAndUserIsNull(String name);
    boolean existsByNameAndUserId(String name, Long userId);
    boolean existsByNameAndUserIsNull(String name);
}
