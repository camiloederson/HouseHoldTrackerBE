package com.mikadev.householdtracker.household.dto;

import java.time.LocalDateTime;

public record HouseholdGetDTO(

        Long id,
        String name,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}