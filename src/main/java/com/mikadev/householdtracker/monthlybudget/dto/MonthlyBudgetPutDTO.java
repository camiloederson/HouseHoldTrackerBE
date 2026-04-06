package com.mikadev.householdtracker.monthlybudget.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record MonthlyBudgetPutDTO(

        @NotNull(message = "Year is required")
        Integer year,

        @NotNull(message = "Month is required")
        @Min(value = 1, message = "Month must be between 1 and 12")
        @Max(value = 12, message = "Month must be between 1 and 12")
        Integer month,

        @NotNull(message = "Planned amount is required")
        BigDecimal plannedAmount,

        @NotNull(message = "Household id is required")
        Long householdId

) {
}