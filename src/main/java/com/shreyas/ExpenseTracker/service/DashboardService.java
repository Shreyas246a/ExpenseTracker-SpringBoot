package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.DTO.Response.DashboardSummaryDTO;



public interface DashboardService {
    public DashboardSummaryDTO getDashboardSummary(Integer startDate, Integer endDate);
}
