package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.entity.Expense;
import com.shreyas.ExpenseTracker.repository.ExpenseRepository;
import com.shreyas.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Override
    public Expense AddExpense(Expense expense) {
        return expenseRepository.save(expense);
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow();
    }

    @Override
    public void deleteExpenseById(Long id) {

    }

    @Override
    public Expense updateExpense(Expense expense) {
        return null;
    }
}
