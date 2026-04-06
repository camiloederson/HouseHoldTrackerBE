package com.mikadev.householdtracker.report;

import com.mikadev.householdtracker.report.ReportService;
import com.mikadev.householdtracker.report.dto.MonthlyReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/monthly/{budgetId}")
    public MonthlyReportDTO getMonthlyReport(@PathVariable Long budgetId) {
        return reportService.getMonthlyReport(budgetId);
    }
}
