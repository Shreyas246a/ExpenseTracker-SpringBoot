package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.ExpenseMapper;
import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.Exceptions.ResourceNotFoundException;
import com.shreyas.ExpenseTracker.Utils.AuthUtil;
import com.shreyas.ExpenseTracker.Utils.JwtUtil;
import com.shreyas.ExpenseTracker.entity.Category;
import com.shreyas.ExpenseTracker.entity.Expense;
import com.shreyas.ExpenseTracker.entity.User;
import com.shreyas.ExpenseTracker.repository.CategoryRepository;
import com.shreyas.ExpenseTracker.repository.ExpenseRepository;
import com.shreyas.ExpenseTracker.repository.UserRepository;
import com.shreyas.ExpenseTracker.service.ExpenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class ExpenseImpl implements ExpenseService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    CategoryRepository categoryRepository;
    @Override
    public ExpenseResponseDTO AddExpense(ExpenseRequestDTO expense) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));

        Expense expense1 = ExpenseMapper.toExpenseEntity(expense);
        expense1.setUser(user);
        Category c = categoryRepository.findByName(expense.getCategory()).orElseThrow(()->new ResourceNotFoundException("Category not found"));
        expense1.setCategory(c);
        expense1 = expenseRepository.save(expense1);
        return ExpenseMapper.toExpenseResponseDTO(expense1);
    }

    @Override
    public List<ExpenseResponseDTO> getAllExpenesesByUser() {
       String email = SecurityContextHolder.getContext().getAuthentication().getName();
       User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
       Long userId = user.getId();
        List<Expense> expenses = expenseRepository.findByUser_Id(userId).orElseThrow();
        return expenses.stream().map(expense -> {
            return ExpenseMapper.toExpenseResponseDTO(expense);
        }).toList();
    }

    @Override
    public ExpenseResponseDTO getExpenseById(Long id) throws org.springframework.security.access.AccessDeniedException {
        User user = AuthUtil.getCurrentUser();
        long userId = user.getId();
        Expense expense= expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        if(expense.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this expense");
        }
        return ExpenseMapper.toExpenseResponseDTO(expense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        long userId = user.getId();
        Expense expense= expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        if(expense.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this expense");
        }
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(()->new ResourceNotFoundException("User not found"));
        long userId = user.getId();
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Expense not found"));
        if(existingExpense.getUser().getId() != userId){
            throw new AccessDeniedException("You do not have access to this expense");
        }
        Category c = categoryRepository.findByName(expense.getCategory()).orElseThrow(()->new ResourceNotFoundException("Category not found"));

        existingExpense.setCategory(c);
        existingExpense.setDate(expense.getDate());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setTitle(expense.getTitle());
        existingExpense.setDescription(expense.getDescription());
        existingExpense = expenseRepository.save(existingExpense);

        return ExpenseMapper.toExpenseResponseDTO(existingExpense);
    }
}
