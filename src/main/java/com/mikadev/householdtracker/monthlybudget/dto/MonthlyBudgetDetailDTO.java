package com.mikadev.householdtracker.monthlybudget.dto;

import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategorySummaryDTO;

import java.math.BigDecimal;
import java.util.List;

public record MonthlyBudgetDetailDTO(
        Long id,
        Integer year,
        Integer month,
        BigDecimal plannedAmount,
        Long householdId,
        String householdName,
        BigDecimal totalAllocated,
        BigDecimal totalSpent,
        BigDecimal totalAvailable,
        List<ExpenseCategorySummaryDTO> categories
) {
}