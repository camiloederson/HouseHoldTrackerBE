package com.mikadev.householdtracker.monthlybudget;

import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetGetDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPostDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPutDTO;
import org.springframework.stereotype.Component;

@Component
public class MonthlyBudgetMapper {

    public MonthlyBudgetGetDTO toGetDTO(MonthlyBudgetEntity monthlyBudgetEntity) {
        return new MonthlyBudgetGetDTO(
                monthlyBudgetEntity.getId(),
                monthlyBudgetEntity.getYear(),
                monthlyBudgetEntity.getMonth(),
                monthlyBudgetEntity.getPlannedAmount(),
                monthlyBudgetEntity.getHousehold().getId(),
                monthlyBudgetEntity.getHousehold().getName(),
                monthlyBudgetEntity.getCreatedAt(),
                monthlyBudgetEntity.getUpdatedAt()
        );
    }

    public MonthlyBudgetEntity toEntity(MonthlyBudgetPostDTO monthlyBudgetPostDTO) {
        return MonthlyBudgetEntity.builder()
                .year(monthlyBudgetPostDTO.year())
                .month(monthlyBudgetPostDTO.month())
                .plannedAmount(monthlyBudgetPostDTO.plannedAmount())
                .build();
    }

    public void updateEntityFromPutDTO(MonthlyBudgetEntity monthlyBudgetEntity, MonthlyBudgetPutDTO monthlyBudgetPutDTO) {
        monthlyBudgetEntity.setYear(monthlyBudgetPutDTO.year());
        monthlyBudgetEntity.setMonth(monthlyBudgetPutDTO.month());
        monthlyBudgetEntity.setPlannedAmount(monthlyBudgetPutDTO.plannedAmount());
    }
}