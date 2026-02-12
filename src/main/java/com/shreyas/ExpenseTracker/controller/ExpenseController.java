package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ApiResponse;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

@Autowired
    ExpenseService expenseService;
@PostMapping("/add")
public ResponseEntity<ApiResponse<ExpenseResponseDTO>> addExpense(@Valid @RequestBody ExpenseRequestDTO expense){
    ExpenseResponseDTO savedExpense = expenseService.AddExpense(expense);
    return ResponseEntity.ok(new ApiResponse<>(true,"Expense added successfully",savedExpense, LocalDateTime.now()));
}

@GetMapping("/expenses")
public ResponseEntity<ApiResponse<Page<ExpenseResponseDTO>>> getAllExpensesByUser(
        @RequestParam(required = false,defaultValue = "0") int page,
        @RequestParam(required = false,defaultValue = "10") int size,
        @RequestParam(required = false) Long categoryId,
        @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
        @RequestParam(required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
        @RequestParam(required = false) Double minAmount,
        @RequestParam(required = false) Double maxAmount,
        @RequestParam(defaultValue = "date") String sortBy,
        @RequestParam(defaultValue = "desc") String order
) {
    System.out.println(categoryId + " "+minAmount);
    Page<ExpenseResponseDTO> expenses = expenseService.getAllExpenesesByUser(page, size, categoryId, startDate, endDate, minAmount, maxAmount, sortBy, order);
    return ResponseEntity.ok(new ApiResponse<>(true,"Expenses fetched successfully",expenses,LocalDateTime.now()));
}

@GetMapping("/{id}")
public ResponseEntity<ApiResponse<ExpenseResponseDTO>> getExpenseById(@PathVariable Long id) {
    ExpenseResponseDTO expense = expenseService.getExpenseById(id);
    return ResponseEntity.ok(new ApiResponse<>(true,"Expense fetched successfully",expense, LocalDateTime.now()));
}
@PutMapping("/update/{id}")
public ResponseEntity<ApiResponse<ExpenseResponseDTO>> updateExpense(@PathVariable Long id,@Valid @RequestBody ExpenseRequestDTO expense) {
    ExpenseResponseDTO updatedExpense = expenseService.updateExpense(id,expense);
    return ResponseEntity.ok(new ApiResponse<>(true,"Expense updated successfully",updatedExpense, LocalDateTime.now()));
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<ApiResponse<String>> deleteExpenseById(@PathVariable Long id) {
    expenseService.deleteExpenseById(id);
    return ResponseEntity.ok(new ApiResponse<>(true,"Expense deleted successfully","Expense with id " + id + " deleted successfully.", LocalDateTime.now()));
}
}
