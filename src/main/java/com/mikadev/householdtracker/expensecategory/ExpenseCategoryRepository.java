package com.mikadev.householdtracker.expensecategory;

import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategorySummaryRawDTO;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseCategoryRepository extends JpaRepository<ExpenseCategoryEntity, Long> {

    List<ExpenseCategoryEntity> findByMonthlyBudget(MonthlyBudgetEntity monthlyBudget);

    Optional<ExpenseCategoryEntity> findByIdAndMonthlyBudget(Long id, MonthlyBudgetEntity monthlyBudget);

    @Query("""
            SELECT new com.mikadev.householdtracker.expensecategory.dto.ExpenseCategorySummaryRawDTO(
                c.id,
                c.name,
                c.allocatedAmount,
                COALESCE(SUM(e.amount), 0)
            )
            FROM ExpenseCategoryEntity c
            LEFT JOIN c.expenses e
            WHERE c.monthlyBudget.id = :monthlyBudgetId
            GROUP BY c.id, c.name, c.allocatedAmount
            ORDER BY c.name
            """)
    List<ExpenseCategorySummaryRawDTO> findCategorySummariesByMonthlyBudgetId(@Param("monthlyBudgetId") Long monthlyBudgetId);
}