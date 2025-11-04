package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.entity.Expense;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.ExpenseRepository;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public Expense AddExpense(Expense expense,long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new RuntimeException("User not found"));
        expense.setUser(user);
        return expenseRepository.save(expense);
    }

    @Override
    public List<Expense> getAllExpenesesByUser(long userId) {
        return expenseRepository.findByUser_Id(userId).orElseThrow();
    }

    @Override
    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found"));
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found"));
        expenseRepository.delete(expense);
    }

    @Override
    public Expense updateExpense(Long id,Expense expense) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(()->new RuntimeException("Expense not found"));

        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDate(expense.getDate());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setTitle(expense.getTitle());
        existingExpense.setDescription(expense.getDescription());
        return expenseRepository.save(existingExpense);
    }
}
