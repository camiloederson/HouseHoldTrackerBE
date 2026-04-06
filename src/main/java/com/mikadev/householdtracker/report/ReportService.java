package com.mikadev.householdtracker.report;

import com.mikadev.householdtracker.expense.ExpenseRepository;
import com.mikadev.householdtracker.expensecategory.ExpenseCategoryRepository;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetRepository;
import com.mikadev.householdtracker.report.dto.CategoryReportDTO;
import com.mikadev.householdtracker.report.dto.MonthlyReportDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;
    private final MonthlyBudgetRepository budgetRepository;

    public MonthlyReportDTO getMonthlyReport(Long budgetId) {
        MonthlyBudgetEntity budget = budgetRepository.findById(budgetId)
                .orElseThrow(() -> new RuntimeException("Budget not found"));

        List<CategoryReportDTO> categoryReports = budget.getExpenseCategories().stream()
                .map(category -> {
                    BigDecimal spent = expenseRepository.sumByCategory(category.getId());
                    BigDecimal allocated = category.getAllocatedAmount();
                    BigDecimal available = allocated.subtract(spent != null ? spent : BigDecimal.ZERO);

                    return new CategoryReportDTO(
                            category.getId(),
                            category.getName(),
                            allocated,
                            spent,
                            available
                    );
                })
                .toList();

        return new MonthlyReportDTO(
                budget.getId(),
                budget.getYear(),
                budget.getMonth(),
                categoryReports
        );
    }
}
