package com.mikadev.householdtracker.auth.dto;

public record LoginResponseDTO(

        String token,
        String tokenType,
        Long userId,
        String firstName,
        String lastName,
        String email

) {
}