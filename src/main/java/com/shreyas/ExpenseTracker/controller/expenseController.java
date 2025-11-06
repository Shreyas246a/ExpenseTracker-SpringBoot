package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.entity.Expense;
import com.shreyas.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class expenseController {

@Autowired
    ExpenseService expenseService;
@PostMapping("/add/{userId}")
public ResponseEntity<ExpenseResponseDTO> addExpense(@RequestBody ExpenseRequestDTO expense, @PathVariable long userId){
    ExpenseResponseDTO savedExpense = expenseService.AddExpense(expense, userId);
    return ResponseEntity.ok(savedExpense);
}
@GetMapping("/user/{userId}")
public ResponseEntity<List<ExpenseResponseDTO>> getAllExpensesByUser(@PathVariable long userId) {
    List<ExpenseResponseDTO> expenses = expenseService.getAllExpenesesByUser(userId);
    return ResponseEntity.ok(expenses);
}
@GetMapping("/{id}")
public ResponseEntity<ExpenseResponseDTO> getExpenseById(@PathVariable Long id) {
    ExpenseResponseDTO expense = expenseService.getExpenseById(id);
    return ResponseEntity.ok(expense);
}
@PutMapping("/update/{id}")
public ResponseEntity<ExpenseResponseDTO> updateExpense(@PathVariable Long id,@RequestBody ExpenseRequestDTO expense) {
    ExpenseResponseDTO updatedExpense = expenseService.updateExpense(id,expense);
    return ResponseEntity.ok(updatedExpense);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
    expenseService.deleteExpenseById(id);
    return ResponseEntity.ok("Expense with id " + id + " deleted successfully.");
}
}
