package com.mikadev.householdtracker.household;

import com.mikadev.householdtracker.household.dto.HouseholdGetDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPostDTO;
import com.mikadev.householdtracker.household.dto.HouseholdPutDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @GetMapping
    public ResponseEntity<List<HouseholdGetDTO>> findAll() {
        return ResponseEntity.ok(householdService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<HouseholdGetDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(householdService.findById(id));
    }

    @PostMapping
    public ResponseEntity<HouseholdGetDTO> save(@Valid @RequestBody HouseholdPostDTO householdPostDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(householdService.save(householdPostDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<HouseholdGetDTO> update(@PathVariable Long id,
                                                  @Valid @RequestBody HouseholdPutDTO householdPutDTO) {
        return ResponseEntity.ok(householdService.update(id, householdPutDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        householdService.delete(id);
        return ResponseEntity.noContent().build();
    }
}