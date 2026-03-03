package com.shreyas.ExpenseTracker.service.impl;

import com.shreyas.ExpenseTracker.DTO.Response.DashboardSummaryDTO;
import com.shreyas.ExpenseTracker.Utils.AuthUtil;
import com.shreyas.ExpenseTracker.repository.ExpenseRepository;
import com.shreyas.ExpenseTracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class DashboardServiceImpl implements DashboardService {
    @Autowired
    ExpenseRepository expenseRepository;
    @Autowired
    AuthUtil authUtil;
    @Override
    public DashboardSummaryDTO getDashboardSummary(Integer month, Integer year) {

        LocalDate now = LocalDate.now();

        // default values
        int currentMonth = (month != null) ? month : now.getMonthValue();
        int currentYear = (year != null) ? year : now.getYear();

        // month start & end
        LocalDate monthStart = LocalDate.of(currentYear, currentMonth, 1);
        LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

        // year start & end
        LocalDate yearStart = LocalDate.of(currentYear, 1, 1);
        LocalDate yearEnd = LocalDate.of(currentYear, 12, 31);

        DashboardSummaryDTO dt = new DashboardSummaryDTO();

        Long userId = authUtil.getCurrentUser().getId();

        dt.setTotalExpense(
                expenseRepository.totalExpensesByUser(userId));

        dt.setMonthlyExpense(
                expenseRepository.totalExpensesByUserAndDateRange(
                        userId, monthStart, monthEnd));

        dt.setYearlyExpense(
                expenseRepository.totalExpensesByUserAndDateRange(
                        userId, yearStart, yearEnd));

        return dt;
    }
}
