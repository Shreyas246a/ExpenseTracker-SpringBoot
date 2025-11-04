package com.shreyas.ExpenseTracker.controller;

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
public ResponseEntity<Expense> addExpense(@RequestBody Expense expense,@PathVariable long userId){
    Expense savedExpense = expenseService.AddExpense(expense, userId);
    return ResponseEntity.ok(savedExpense);
}
@GetMapping("/user/{userId}")
public ResponseEntity<List<Expense>> getAllExpensesByUser(@PathVariable long userId) {
    List<Expense> expenses = expenseService.getAllExpenesesByUser(userId);
    return ResponseEntity.ok(expenses);
}
@GetMapping("/{id}")
public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
    Expense expense = expenseService.getExpenseById(id);
    return ResponseEntity.ok(expense);
}
@PutMapping("/update/{id}")
public ResponseEntity<Expense> updateExpense(@PathVariable Long id,@RequestBody Expense expense) {
    Expense updatedExpense = expenseService.updateExpense(id,expense);
    return ResponseEntity.ok(updatedExpense);
}

@DeleteMapping("/delete/{id}")
public ResponseEntity<String> deleteExpenseById(@PathVariable Long id) {
    expenseService.deleteExpenseById(id);
    return ResponseEntity.ok("Expense with id " + id + " deleted successfully.");
}
}
