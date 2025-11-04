package com.shreyas.ExpenseTracker.repository;

import com.shreyas.ExpenseTracker.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    public Optional<List<Expense>> findByUser_Id(Long userId);
}
