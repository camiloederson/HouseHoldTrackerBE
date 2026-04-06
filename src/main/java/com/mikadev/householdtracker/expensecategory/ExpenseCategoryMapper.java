package com.mikadev.householdtracker.expensecategory;

import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryGetDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPostDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPutDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseCategoryMapper {

    public ExpenseCategoryGetDTO toGetDTO(ExpenseCategoryEntity expenseCategoryEntity) {
        return new ExpenseCategoryGetDTO(
                expenseCategoryEntity.getId(),
                expenseCategoryEntity.getName(),
                expenseCategoryEntity.getAllocatedAmount(),
                expenseCategoryEntity.getMonthlyBudget().getId(),
                expenseCategoryEntity.getMonthlyBudget().getYear(),
                expenseCategoryEntity.getMonthlyBudget().getMonth(),
                expenseCategoryEntity.getCreatedAt(),
                expenseCategoryEntity.getUpdatedAt()
        );
    }

    public ExpenseCategoryEntity toEntity(ExpenseCategoryPostDTO expenseCategoryPostDTO) {
        return ExpenseCategoryEntity.builder()
                .name(expenseCategoryPostDTO.name())
                .allocatedAmount(expenseCategoryPostDTO.allocatedAmount())
                .build();
    }

    public void updateEntityFromPutDTO(ExpenseCategoryEntity expenseCategoryEntity,
                                       ExpenseCategoryPutDTO expenseCategoryPutDTO) {
        expenseCategoryEntity.setName(expenseCategoryPutDTO.name());
        expenseCategoryEntity.setAllocatedAmount(expenseCategoryPutDTO.allocatedAmount());
    }
}