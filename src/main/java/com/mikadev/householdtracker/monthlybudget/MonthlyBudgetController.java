package com.mikadev.householdtracker.monthlybudget;

import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetDetailDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetGetDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPostDTO;
import com.mikadev.householdtracker.monthlybudget.dto.MonthlyBudgetPutDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monthly-budgets")
public class MonthlyBudgetController {

    private final MonthlyBudgetService monthlyBudgetService;

    public MonthlyBudgetController(MonthlyBudgetService monthlyBudgetService) {
        this.monthlyBudgetService = monthlyBudgetService;
    }

    @GetMapping
    public ResponseEntity<List<MonthlyBudgetGetDTO>> findAll() {
        return ResponseEntity.ok(monthlyBudgetService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonthlyBudgetGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(monthlyBudgetService.findById(id));
    }

    @GetMapping("/{id}/detail")
    public ResponseEntity<MonthlyBudgetDetailDTO> findDetailById(@PathVariable Long id) {
        return ResponseEntity.ok(monthlyBudgetService.findDetailById(id));
    }

    @PostMapping
    public ResponseEntity<MonthlyBudgetGetDTO> save(@Valid @RequestBody MonthlyBudgetPostDTO monthlyBudgetPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(monthlyBudgetService.save(monthlyBudgetPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MonthlyBudgetGetDTO> update(@PathVariable Long id,
                                                      @Valid @RequestBody MonthlyBudgetPutDTO monthlyBudgetPutDTO) {
        return ResponseEntity.ok(monthlyBudgetService.update(id, monthlyBudgetPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        monthlyBudgetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}