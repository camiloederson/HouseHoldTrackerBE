package com.mikadev.householdtracker.report.dto;

import java.util.List;

public record MonthlyReportDTO(
        Long budgetId,
        Integer year,
        Integer month,
        List<CategoryReportDTO> categories
) {
}
