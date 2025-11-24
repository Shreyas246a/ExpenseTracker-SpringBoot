package com.shreyas.ExpenseTracker.repository;

import com.shreyas.ExpenseTracker.entity.Expense;
import com.shreyas.ExpenseTracker.entity.User;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    Page<Expense> findByUser_Id(Long id, Pageable pageable);
    public boolean existsByCategory_Id(Long categoryId);
}
