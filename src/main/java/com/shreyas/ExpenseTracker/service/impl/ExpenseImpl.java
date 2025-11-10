package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.ExpenseMapper;
import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.Exceptions.ResourceNotFoundException;
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
    public ExpenseResponseDTO AddExpense(ExpenseRequestDTO expense,long userId) {
        User user = userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User not found"));

        Expense expense1 = ExpenseMapper.toExpenseEntity(expense);
        expense1.setUser(user);
        expense1 = expenseRepository.save(expense1);
        ExpenseResponseDTO responseDTO = ExpenseMapper.toExpenseResponseDTO(expense1);
        return responseDTO;
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenesesByUser(long userId) {
        List<Expense> expenses = expenseRepository.findByUser_Id(userId).orElseThrow();
        return expenses.stream().map(expense -> {
            return ExpenseMapper.toExpenseResponseDTO(expense);
        }).toList();
    }

    @Override
    public ExpenseResponseDTO getExpenseById(Long id) {
        Expense expense= expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        return ExpenseMapper.toExpenseResponseDTO(expense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense) {
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));

        existingExpense.setCategory(expense.getCategory());
        existingExpense.setDate(expense.getDate());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setTitle(expense.getTitle());
        existingExpense.setDescription(expense.getDescription());
        existingExpense = expenseRepository.save(existingExpense);

        return ExpenseMapper.toExpenseResponseDTO(existingExpense);
    }
}
