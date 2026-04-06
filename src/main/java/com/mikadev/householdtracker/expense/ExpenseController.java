package com.mikadev.householdtracker.expense;

import com.mikadev.householdtracker.expense.dto.ExpenseGetDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePostDTO;
import com.mikadev.householdtracker.expense.dto.ExpensePutDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expenses")
public class ExpenseController {

    private final ExpenseService expenseService;

    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseGetDTO>> findAll() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseGetDTO> save(@Valid @RequestBody ExpensePostDTO expensePostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseService.save(expensePostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseGetDTO> update(@PathVariable Long id,
                                                @Valid @RequestBody ExpensePutDTO expensePutDTO) {
        return ResponseEntity.ok(expenseService.update(id, expensePutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}