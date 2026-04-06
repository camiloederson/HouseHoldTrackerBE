package com.mikadev.householdtracker.expensecategory.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ExpenseCategoryGetDTO(

        Long id,
        String name,
        BigDecimal allocatedAmount,
        Long monthlyBudgetId,
        Integer budgetYear,
        Integer budgetMonth,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}