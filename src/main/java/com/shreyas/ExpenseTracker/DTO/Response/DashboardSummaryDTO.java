package com.shreyas.ExpenseTracker.DTO.Response;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DashboardSummaryDTO {

    private Double totalExpense;
    private Double yearlyExpense;
    private Double monthlyExpense;
}