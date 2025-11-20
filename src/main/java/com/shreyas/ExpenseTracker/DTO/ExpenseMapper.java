package com.shreyas.ExpenseTracker.DTO;

import com.shreyas.ExpenseTracker.DTO.Request.ExpenseRequestDTO;
import com.shreyas.ExpenseTracker.DTO.Response.ExpenseResponseDTO;
import com.shreyas.ExpenseTracker.entity.Expense;

public class ExpenseMapper {
    public static ExpenseResponseDTO toExpenseResponseDTO(Expense expense) {
            ExpenseResponseDTO response = new ExpenseResponseDTO();
            response.setId(expense.getId());
            response.setAmount(expense.getAmount());
            response.setDescription(expense.getDescription());
            response.setTitle(expense.getTitle());
            response.setCategory(expense.getCategory().getName());
            response.setDate(expense.getDate());
            response.setUserName(expense.getUser().getName());
            return response;
    }
    public static Expense toExpenseEntity(ExpenseRequestDTO expenseDTO) {
        Expense expense = new Expense();
        expense.setAmount(expenseDTO.getAmount());
        expense.setDescription(expenseDTO.getDescription());
        expense.setTitle(expenseDTO.getTitle());
        expense.setDate(expenseDTO.getDate());
        return expense;
    }
}
