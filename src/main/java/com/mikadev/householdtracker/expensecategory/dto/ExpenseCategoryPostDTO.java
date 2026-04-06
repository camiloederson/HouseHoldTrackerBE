package com.mikadev.householdtracker.expensecategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ExpenseCategoryPostDTO(

        @NotBlank(message = "Category name is required")
        @Size(max = 100, message = "Category name must not exceed 100 characters")
        String name,

        @NotNull(message = "Allocated amount is required")
        BigDecimal allocatedAmount,

        @NotNull(message = "Monthly budget id is required")
        Long monthlyBudgetId

) {
}