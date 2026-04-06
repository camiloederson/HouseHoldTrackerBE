package com.mikadev.householdtracker.report.dto;

import java.math.BigDecimal;

public record CategoryReportDTO(
        Long categoryId,
        String categoryName,
        BigDecimal allocatedAmount,
        BigDecimal spentAmount,
        BigDecimal availableAmount
        ) {
}
