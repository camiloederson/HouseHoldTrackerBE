package com.mikadev.householdtracker.household.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record HouseholdPutDTO(

        @NotBlank(message = "Household name is required")
        @Size(max = 150, message = "Household name must not exceed 150 characters")
        String name

) {
}