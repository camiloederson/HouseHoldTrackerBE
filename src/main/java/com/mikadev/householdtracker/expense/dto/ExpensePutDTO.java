package com.mikadev.householdtracker.expense.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record ExpensePutDTO(

        @NotBlank(message = "Title is required")
        @Size(max = 150, message = "Title must not exceed 150 characters")
        String title,

        @Size(max = 500, message = "Description must not exceed 500 characters")
        String description,

        @NotNull(message = "Amount is required")
        BigDecimal amount,

        @NotNull(message = "Expense date is required")
        LocalDate expenseDate,

        @NotNull(message = "Monthly budget id is required")
        Long monthlyBudgetId,

        @NotNull(message = "Category id is required")
        Long categoryId,

        @NotNull(message = "Created by user id is required")
        Long createdByUserId

) {
}