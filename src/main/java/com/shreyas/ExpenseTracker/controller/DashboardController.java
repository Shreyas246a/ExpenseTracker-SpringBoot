package com.shreyas.ExpenseTracker.controller;

import com.shreyas.ExpenseTracker.DTO.Response.ApiResponse;
import com.shreyas.ExpenseTracker.DTO.Response.DashboardSummaryDTO;
import com.shreyas.ExpenseTracker.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
@Autowired
    DashboardService dashboardService;

@GetMapping("/summary")
    public ResponseEntity<ApiResponse<DashboardSummaryDTO>> getDashboardSummary(@RequestParam(required = false,name = "month") Integer month,
                                                                              @RequestParam(required = false, name = "year") Integer year) {
    DashboardSummaryDTO d = dashboardService.getDashboardSummary(month, year);
    return ResponseEntity.ok(new ApiResponse<>(true,"Dashboard summary fetched successfully",d, LocalDateTime.now()));
    }
}
