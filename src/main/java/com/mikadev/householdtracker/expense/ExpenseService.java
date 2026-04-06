package com.mikadev.householdtracker.expense;

import com.mikadev.householdtracker.exception.BadRequestException;
import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import com.mikadev.householdtracker.expense.dto.ExpenseGetDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePostDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePutDTO;
import com.mikadev.householdtracker.expensecategory.ExpenseCategoryEntity;
import com.mikadev.householdtracker.expensecategory.ExpenseCategoryRepository;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetRepository;
import com.mikadev.householdtracker.user.UserEntity;
import com.mikadev.householdtracker.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final UserRepository userRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseService(ExpenseRepository expenseRepository,
                          MonthlyBudgetRepository monthlyBudgetRepository,
                          ExpenseCategoryRepository expenseCategoryRepository,
                          UserRepository userRepository,
                          ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.userRepository = userRepository;
        this.expenseMapper = expenseMapper;
    }

    public List<ExpenseGetDTO> findAll() {
        List<ExpenseEntity> expenseEntities = expenseRepository.findAll();
        List<ExpenseGetDTO> expenseGetDTOS = new ArrayList<>();

        for (ExpenseEntity expenseEntity : expenseEntities) {
            expenseGetDTOS.add(expenseMapper.toGetDTO(expenseEntity));
        }

        return expenseGetDTOS;
    }

    public ExpenseGetDTO findById(Long id) {
        ExpenseEntity expenseEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        return expenseMapper.toGetDTO(expenseEntity);
    }

    public ExpenseGetDTO save(ExpensePostDTO expensePostDTO) {
        MonthlyBudgetEntity monthlyBudgetEntity = monthlyBudgetRepository.findById(expensePostDTO.monthlyBudgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + expensePostDTO.monthlyBudgetId()));

        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryRepository.findById(expensePostDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense category not found with id: " + expensePostDTO.categoryId()));

        UserEntity userEntity = userRepository.findById(expensePostDTO.createdByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + expensePostDTO.createdByUserId()));

        validateRelations(monthlyBudgetEntity, expenseCategoryEntity, userEntity);

        ExpenseEntity expenseEntity = expenseMapper.toEntity(expensePostDTO);
        expenseEntity.setMonthlyBudget(monthlyBudgetEntity);
        expenseEntity.setCategory(expenseCategoryEntity);
        expenseEntity.setCreatedBy(userEntity);

        ExpenseEntity savedExpense = expenseRepository.save(expenseEntity);

        return expenseMapper.toGetDTO(savedExpense);
    }

    public ExpenseGetDTO update(Long id, ExpensePutDTO expensePutDTO) {
        ExpenseEntity expenseEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        MonthlyBudgetEntity monthlyBudgetEntity = monthlyBudgetRepository.findById(expensePutDTO.monthlyBudgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + expensePutDTO.monthlyBudgetId()));

        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryRepository.findById(expensePutDTO.categoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Expense category not found with id: " + expensePutDTO.categoryId()));

        UserEntity userEntity = userRepository.findById(expensePutDTO.createdByUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + expensePutDTO.createdByUserId()));

        validateRelations(monthlyBudgetEntity, expenseCategoryEntity, userEntity);

        expenseMapper.updateEntityFromPutDTO(expenseEntity, expensePutDTO);
        expenseEntity.setMonthlyBudget(monthlyBudgetEntity);
        expenseEntity.setCategory(expenseCategoryEntity);
        expenseEntity.setCreatedBy(userEntity);

        ExpenseEntity updatedExpense = expenseRepository.save(expenseEntity);

        return expenseMapper.toGetDTO(updatedExpense);
    }

    public void delete(Long id) {
        ExpenseEntity expenseEntity = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        expenseRepository.delete(expenseEntity);
    }

    private void validateRelations(MonthlyBudgetEntity monthlyBudgetEntity,
                                   ExpenseCategoryEntity expenseCategoryEntity,
                                   UserEntity userEntity) {

        if (!expenseCategoryEntity.getMonthlyBudget().getId().equals(monthlyBudgetEntity.getId())) {
            throw new BadRequestException("The selected category does not belong to the selected monthly budget");
        }

        if (!userEntity.getHousehold().getId().equals(monthlyBudgetEntity.getHousehold().getId())) {
            throw new BadRequestException("The selected user does not belong to the household of the selected monthly budget");
        }
    }
}