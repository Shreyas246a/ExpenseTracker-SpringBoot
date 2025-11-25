package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.entity.Expense;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;


public interface ExpenseService {
    public ExpenseResponseDTO AddExpense(ExpenseRequestDTO expense);
    public ExpenseResponseDTO getExpenseById(Long id);
    public void deleteExpenseById(Long id);
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense);
    Page<ExpenseResponseDTO> getAllExpenesesByUser(int page, int size, Long category, LocalDate startDate, LocalDate endDate, Double minAmount, Double maxAmount, String sortBy, String order);
}
