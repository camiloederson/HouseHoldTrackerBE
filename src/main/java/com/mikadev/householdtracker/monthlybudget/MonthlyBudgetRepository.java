package com.mikadev.householdtracker.monthlybudget;

import com.mikadev.householdtracker.household.HouseholdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MonthlyBudgetRepository extends JpaRepository<MonthlyBudgetEntity, Long> {

    Optional<MonthlyBudgetEntity> findByHouseholdAndYearAndMonth(
            HouseholdEntity household,
            Integer year,
            Integer month
    );
}