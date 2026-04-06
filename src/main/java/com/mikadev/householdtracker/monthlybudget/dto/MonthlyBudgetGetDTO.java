package com.mikadev.householdtracker.monthlybudget.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record MonthlyBudgetGetDTO(

        Long id,
        Integer year,
        Integer month,
        BigDecimal plannedAmount,
        Long householdId,
        String householdName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}