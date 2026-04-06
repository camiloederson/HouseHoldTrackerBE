package com.mikadev.householdtracker.monthlybudget;

import com.mikadev.householdtracker.exception.ResourceNotFoundException;
import com.mikadev.householdtracker.expensecategory.ExpenseCategoryRepository;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategorySummaryDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategorySummaryRawDTO;
import com.mikadev.householdtracker.household.HouseholdEntity;
import com.mikadev.householdtracker.household.HouseholdRepository;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetDetailDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetGetDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPostDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPutDTO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MonthlyBudgetService {

    private final MonthlyBudgetRepository monthlyBudgetRepository;
    private final HouseholdRepository householdRepository;
    private final MonthlyBudgetMapper monthlyBudgetMapper;
    private final ExpenseCategoryRepository expenseCategoryRepository;

    public MonthlyBudgetService(MonthlyBudgetRepository monthlyBudgetRepository,
                                HouseholdRepository householdRepository,
                                MonthlyBudgetMapper monthlyBudgetMapper,
                                ExpenseCategoryRepository expenseCategoryRepository) {
        this.monthlyBudgetRepository = monthlyBudgetRepository;
        this.householdRepository = householdRepository;
        this.monthlyBudgetMapper = monthlyBudgetMapper;
        this.expenseCategoryRepository = expenseCategoryRepository;
    }

    public List<MonthlyBudgetGetDTO> findAll() {
        List<MonthlyBudgetEntity> entities = monthlyBudgetRepository.findAll();
        List<MonthlyBudgetGetDTO> dtoList = new ArrayList<>();

        for (MonthlyBudgetEntity entity : entities) {
            dtoList.add(monthlyBudgetMapper.toGetDTO(entity));
        }

        return dtoList;
    }

    public MonthlyBudgetGetDTO findById(Long id) {
        MonthlyBudgetEntity entity = monthlyBudgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + id));

        return monthlyBudgetMapper.toGetDTO(entity);
    }

    public MonthlyBudgetGetDTO save(MonthlyBudgetPostDTO monthlyBudgetPostDTO) {
        HouseholdEntity household = householdRepository.findById(monthlyBudgetPostDTO.householdId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Household not found with id: " + monthlyBudgetPostDTO.householdId()
                ));

        MonthlyBudgetEntity entity = monthlyBudgetMapper.toEntity(monthlyBudgetPostDTO);
        entity.setHousehold(household);

        MonthlyBudgetEntity savedEntity = monthlyBudgetRepository.save(entity);
        return monthlyBudgetMapper.toGetDTO(savedEntity);
    }

    public MonthlyBudgetGetDTO update(Long id, MonthlyBudgetPutDTO monthlyBudgetPutDTO) {
        MonthlyBudgetEntity entity = monthlyBudgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + id));

        monthlyBudgetMapper.updateEntityFromPutDTO(entity, monthlyBudgetPutDTO);

        MonthlyBudgetEntity updatedEntity = monthlyBudgetRepository.save(entity);
        return monthlyBudgetMapper.toGetDTO(updatedEntity);
    }

    public void delete(Long id) {
        MonthlyBudgetEntity entity = monthlyBudgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + id));

        monthlyBudgetRepository.delete(entity);
    }

    public MonthlyBudgetDetailDTO findDetailById(Long id) {
        MonthlyBudgetEntity monthlyBudgetEntity = monthlyBudgetRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monthly budget not found with id: " + id));

        List<ExpenseCategorySummaryRawDTO> rawCategories =
                expenseCategoryRepository.findCategorySummariesByMonthlyBudgetId(id);

        List<ExpenseCategorySummaryDTO> categories = new ArrayList<>();

        BigDecimal totalAllocated = BigDecimal.ZERO;
        BigDecimal totalSpent = BigDecimal.ZERO;

        for (ExpenseCategorySummaryRawDTO raw : rawCategories) {
            BigDecimal spent = raw.spentSoFar() == null ? BigDecimal.ZERO : raw.spentSoFar();
            BigDecimal available = raw.allocatedAmount().subtract(spent);

            categories.add(new ExpenseCategorySummaryDTO(
                    raw.id(),
                    raw.name(),
                    raw.allocatedAmount(),
                    spent,
                    available
            ));

            totalAllocated = totalAllocated.add(raw.allocatedAmount());
            totalSpent = totalSpent.add(spent);
        }

        BigDecimal totalAvailable = totalAllocated.subtract(totalSpent);

        return new MonthlyBudgetDetailDTO(
                monthlyBudgetEntity.getId(),
                monthlyBudgetEntity.getYear(),
                monthlyBudgetEntity.getMonth(),
                monthlyBudgetEntity.getPlannedAmount(),
                monthlyBudgetEntity.getHousehold().getId(),
                monthlyBudgetEntity.getHousehold().getName(),
                totalAllocated,
                totalSpent,
                totalAvailable,
                categories
        );
    }
}