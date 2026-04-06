package com.mikadev.householdtracker.expensecategory.dto;

import java.math.BigDecimal;

public record ExpenseCategorySummaryDTO(
        Long id,
        String name,
        BigDecimal allocatedAmount,
        BigDecimal spentSoFar,
        BigDecimal available
) {
}