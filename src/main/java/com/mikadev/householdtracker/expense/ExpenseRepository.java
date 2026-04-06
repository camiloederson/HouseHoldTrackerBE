package com.mikadev.householdtracker.expense;

import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import com.mikadev.householdtracker.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Long> {

    List<ExpenseEntity> findByMonthlyBudget(MonthlyBudgetEntity monthlyBudget);

    List<ExpenseEntity> findByCreatedBy(UserEntity user);

    @Query("SELECT COALESCE(SUM(e.amount), 0) FROM ExpenseEntity e WHERE e.category.id = :categoryId")
    BigDecimal sumByCategory(@Param("categoryId") Long categoryId);
}