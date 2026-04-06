package com.mikadev.householdtracker.expensecategory.dto;

import java.math.BigDecimal;

public record ExpenseCategorySummaryRawDTO(
        Long id,
        String name,
        BigDecimal allocatedAmount,
        BigDecimal spentSoFar
) {
}