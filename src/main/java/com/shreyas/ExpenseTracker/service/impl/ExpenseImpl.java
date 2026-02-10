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
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ExpenseImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final AuthUtil authUtil;

    public ExpenseImpl(ExpenseRepository expenseRepository, UserRepository userRepository, JwtUtil jwtUtil, CategoryRepository categoryRepository, AuthUtil authUtil) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.authUtil = authUtil;
    }

    @Override
    public ExpenseResponseDTO AddExpense(ExpenseRequestDTO expense) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Expense expense1 = ExpenseMapper.toExpenseEntity(expense);
        expense1.setUser(user);
        Category c = categoryRepository
                .findByNameAndUserId(expense.getCategory(), user.getId())
                .orElseGet(() ->
                        categoryRepository.findByNameAndUserIsNull(expense.getCategory())
                                .orElseThrow(() -> new RuntimeException("Category not found"))
                );

        expense1.setCategory(c);
        expense1 = expenseRepository.save(expense1);
        return ExpenseMapper.toExpenseResponseDTO(expense1);
    }


    @Override
    public ExpenseResponseDTO getExpenseById(Long id) throws org.springframework.security.access.AccessDeniedException {
        User user = authUtil.getCurrentUser();
        long userId = user.getId();
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (expense.getUser().getId() != userId) {
            throw new AccessDeniedException("You do not have access to this expense");
        }
        return ExpenseMapper.toExpenseResponseDTO(expense);
    }

    @Override
    public void deleteExpenseById(Long id) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        long userId = user.getId();
        Expense expense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (expense.getUser().getId() != userId) {
            throw new AccessDeniedException("You do not have access to this expense");
        }
        expenseRepository.delete(expense);
    }

    @Override
    public ExpenseResponseDTO updateExpense(Long id, ExpenseRequestDTO expense) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        long userId = user.getId();
        Expense existingExpense = expenseRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Expense not found"));
        if (existingExpense.getUser().getId() != userId) {
            throw new AccessDeniedException("You do not have access to this expense");
        }
        Category c = categoryRepository.findByNameAndUserId(expense.getCategory(),user.getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));

        existingExpense.setCategory(c);
        existingExpense.setDate(expense.getDate());
        existingExpense.setAmount(expense.getAmount());
        existingExpense.setTitle(expense.getTitle());
        existingExpense.setDescription(expense.getDescription());
        existingExpense = expenseRepository.save(existingExpense);

        return ExpenseMapper.toExpenseResponseDTO(existingExpense);
    }

    @Override
    public Page<ExpenseResponseDTO> getAllExpenesesByUser(
            int page, int size, Long category, LocalDate startDate, LocalDate endDate, Double minAmount, Double maxAmount, String sortBy, String order) {
        User user = authUtil.getCurrentUser();

        Sort sort = order.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Expense> expenses = expenseRepository.filterExpenses(user.getId(), category, startDate, endDate, minAmount, maxAmount, pageable);

        return expenses.map((ExpenseMapper::toExpenseResponseDTO));
    }
}