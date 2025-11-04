package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.entity.Expense;

import java.util.List;


public interface ExpenseService {
    public Expense AddExpense(Expense expense,long userId);
    public List<Expense> getAllExpenesesByUser(long userId);
    public Expense getExpenseById(Long id);
    public void deleteExpenseById(Long id);
    public Expense updateExpense(Long id,Expense expense);
}
