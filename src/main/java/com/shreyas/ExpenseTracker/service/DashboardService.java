package com.shreyas.ExpenseTracker.service;

import com.shreyas.ExpenseTracker.DTO.Response.DashboardSummaryDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


public interface DashboardService {
    public DashboardSummaryDTO getDashboardSummary(Integer startDate, Integer endDate);
}
