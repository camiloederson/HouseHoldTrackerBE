package com.mikadev.householdtracker.expensecategory;

import com.mikadev.householdtracker.exception.BadRequestException;
import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryGetDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPostDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPutDTO;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetEntity;
import com.mikadev.householdtracker.monthlybudget.MonthlyBudgetRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExpenseCategoryService {

    private final ExpenseCategoryRepository expenseCategoryRepository;
    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final ExpenseCategoryMapper expenseCategoryMapper;

    public ExpenseCategoryService(ExpenseCategoryRepository expenseCategoryRepository,
                                  MonthlyBudgetRepository monthlyBudgetRepository,
                                  ExpenseCategoryMapper expenseCategoryMapper) {
        this.expenseCategoryRepository = expenseCategoryRepository;
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.expenseCategoryMapper = expenseCategoryMapper;
    }

    public List<ExpenseCategoryGetDTO> findAll() {
        List<ExpenseCategoryEntity> expenseCategoryEntities = expenseCategoryRepository.findAll();
        List<ExpenseCategoryGetDTO> expenseCategoryGetDTOS = new ArrayList<>();

        for (ExpenseCategoryEntity expenseCategoryEntity : expenseCategoryEntities) {
            expenseCategoryGetDTOS.add(expenseCategoryMapper.toGetDTO(expenseCategoryEntity));
        }

        return expenseCategoryGetDTOS;
    }

    public ExpenseCategoryGetDTO findById(Long id) {
        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense category not found with id: " + id));

        return expenseCategoryMapper.toGetDTO(expenseCategoryEntity);
    }

    public ExpenseCategoryGetDTO save(ExpenseCategoryPostDTO expenseCategoryPostDTO) {
        MonthlyBudgetEntity monthlyBudgetEntity = monthlyBudgetRepository.findById(expenseCategoryPostDTO.monthlyBudgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + expenseCategoryPostDTO.monthlyBudgetId()));

        validateUniqueNameWithinBudget(null, expenseCategoryPostDTO.name(), monthlyBudgetEntity);

        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryMapper.toEntity(expenseCategoryPostDTO);
        expenseCategoryEntity.setMonthlyBudget(monthlyBudgetEntity);

        ExpenseCategoryEntity savedExpenseCategory = expenseCategoryRepository.save(expenseCategoryEntity);

        return expenseCategoryMapper.toGetDTO(savedExpenseCategory);
    }

    public ExpenseCategoryGetDTO update(Long id, ExpenseCategoryPutDTO expenseCategoryPutDTO) {
        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense category not found with id: " + id));

        MonthlyBudgetEntity monthlyBudgetEntity = monthlyBudgetRepository.findById(expenseCategoryPutDTO.monthlyBudgetId())
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + expenseCategoryPutDTO.monthlyBudgetId()));

        validateUniqueNameWithinBudget(id, expenseCategoryPutDTO.name(), monthlyBudgetEntity);

        expenseCategoryMapper.updateEntityFromPutDTO(expenseCategoryEntity, expenseCategoryPutDTO);
        expenseCategoryEntity.setMonthlyBudget(monthlyBudgetEntity);

        ExpenseCategoryEntity updatedExpenseCategory = expenseCategoryRepository.save(expenseCategoryEntity);

        return expenseCategoryMapper.toGetDTO(updatedExpenseCategory);
    }

    public void delete(Long id) {
        ExpenseCategoryEntity expenseCategoryEntity = expenseCategoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense category not found with id: " + id));

        expenseCategoryRepository.delete(expenseCategoryEntity);
    }

    private void validateUniqueNameWithinBudget(Long currentCategoryId,
                                                String categoryName,
                                                MonthlyBudgetEntity monthlyBudgetEntity) {
        List<ExpenseCategoryEntity> categories = expenseCategoryRepository.findByMonthlyBudget(monthlyBudgetEntity);

        for (ExpenseCategoryEntity category : categories) {
            boolean sameName = category.getName().equalsIgnoreCase(categoryName);
            boolean differentEntity = currentCategoryId == null || !category.getId().equals(currentCategoryId);

            if (sameName && differentEntity) {
                throw new BadRequestException("A category with this name already exists in the selected monthly budget");
            }
        }
    }
}