package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.entity.Expense;


public interface ExpenseService {
    public Expense AddExpense(Expense expense);
    public Expense getExpenseId(Long id);

    Expense getExpenseById(Long id);

    public void deleteExpenseById(Long id);
    public Expense updateExpense(Expense expense);
}
