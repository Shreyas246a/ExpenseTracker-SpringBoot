package com.shreyas.ExpenseTracker.repository;

import com.shreyas.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;

public interface ExpenseRepository extends JpaRepository<Expense,Long> {
    Page<Expense> findByUser_Id(Long id, Pageable pageable);
    public boolean existsByCategory_Id(Long categoryId);

    @Query("""
            SELECT e FROM Expense e
            WHERE e.user.id = :userId
            AND (:category IS NULL OR e.category.id = :category)
            AND (:startDate IS NULL OR e.date >= :startDate)
            AND (:endDate IS NULL OR e.date <= :endDate)
            AND (:minAmount IS NULL OR e.amount >= :minAmount)
            AND (:maxAmount IS NULL OR e.amount <= :maxAmount)
            """)
    public Page<Expense> filterExpenses(
    @Param("userId")       Long userId,
    @Param("category")     Long category,
    @Param("startDate")    LocalDate startDate,
    @Param("endDate")      LocalDate endDate,
    @Param("minAmount")    Double minAmount,
    @Param("maxAmount")    Double maxAmount,
    Pageable pageable
    );


    @Query("""
            SELECT COALESCE(SUM(e.amount), 0) FROM Expense e
            WHERE e.user.id = :userId
            """)
    public double totalExpensesByUser(@Param("userId") Long userId);

    @Query("""
            SELECT COALESCE(SUM(e.amount), 0) FROM Expense e
            WHERE e.user.id = :userId
            AND e.date >= :startDate
            AND e.date <= :endDate
            """)
    public double totalExpensesByUserAndDateRange(
    @Param("userId") Long userId,
    @Param("startDate") LocalDate startDate,
    @Param("endDate") LocalDate endDate
    );
}
