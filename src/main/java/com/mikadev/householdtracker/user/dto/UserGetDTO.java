package com.mikadev.householdtracker.user.dto;

import java.time.LocalDateTime;

public record UserGetDTO(

        Long id,
        String firstName,
        String lastName,
        String email,
        Boolean enabled,
        Long householdId,
        String householdName,
        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {
}