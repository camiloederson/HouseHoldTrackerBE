package com.mikadev.householdtracker.expensecategory;

import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryGetDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPostDTO;
import com.mikadev.householdtracker.expensecategory.dto.ExpenseCategoryPutDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expense-categories")
public class ExpenseCategoryController {

    private final ExpenseCategoryService expenseCategoryService;

    public ExpenseCategoryController(ExpenseCategoryService expenseCategoryService) {
        this.expenseCategoryService = expenseCategoryService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseCategoryGetDTO>> findAll() {
        return ResponseEntity.ok(expenseCategoryService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseCategoryGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(expenseCategoryService.findById(id));
    }

    @PostMapping
    public ResponseEntity<ExpenseCategoryGetDTO> save(@Valid @RequestBody ExpenseCategoryPostDTO expenseCategoryPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(expenseCategoryService.save(expenseCategoryPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExpenseCategoryGetDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody ExpenseCategoryPutDTO expenseCategoryPutDTO) {
        return ResponseEntity.ok(expenseCategoryService.update(id, expenseCategoryPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        expenseCategoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}