package com.mikadev.householdtracker.expense;

import com.mikadev.householdtracker.expense.dto.ExpenseGetDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePostDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePutDTO;
import org.springframework.stereotype.Component;

@Component
public class ExpenseMapper {

    public ExpenseGetDTO toGetDTO(ExpenseEntity expenseEntity) {
        return new ExpenseGetDTO(
                expenseEntity.getId(),
                expenseEntity.getTitle(),
                expenseEntity.getDescription(),
                expenseEntity.getAmount(),
                expenseEntity.getExpenseDate(),
                expenseEntity.getMonthlyBudget().getId(),
                expenseEntity.getMonthlyBudget().getYear(),
                expenseEntity.getMonthlyBudget().getMonth(),
                expenseEntity.getCategory().getId(),
                expenseEntity.getCategory().getName(),
                expenseEntity.getCreatedBy().getId(),
                expenseEntity.getCreatedBy().getEmail(),
                expenseEntity.getCreatedAt(),
                expenseEntity.getUpdatedAt()
        );
    }

    public ExpenseEntity toEntity(ExpensePostDTO expensePostDTO) {
        return ExpenseEntity.builder()
                .title(expensePostDTO.title())
                .description(expensePostDTO.description())
                .amount(expensePostDTO.amount())
                .expenseDate(expensePostDTO.expenseDate())
                .build();
    }

    public void updateEntityFromPutDTO(ExpenseEntity expenseEntity, ExpensePutDTO expensePutDTO) {
        expenseEntity.setTitle(expensePutDTO.title());
        expenseEntity.setDescription(expensePutDTO.description());
        expenseEntity.setAmount(expensePutDTO.amount());
        expenseEntity.setExpenseDate(expensePutDTO.expenseDate());
    }
}