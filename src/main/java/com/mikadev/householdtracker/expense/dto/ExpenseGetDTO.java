package com.mikadev.householdtracker.expense.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ExpenseGetDTO(

        Long id,
        String title,
        String description,
        BigDecimal amount,
        LocalDate expenseDate,
        Long monthlyBudgetId,
        Integer budgetYear,
        Integer budgetMonth,
        Long categoryId,
        String categoryName,
        Long createdByUserId,
        String createdByUserEmail,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}