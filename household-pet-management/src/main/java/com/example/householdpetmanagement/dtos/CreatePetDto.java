package com.example.householdpetmanagement.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePetDto(
        @NotBlank(message = "Name is required")
        String name,
        
        @NotBlank(message = "Animal type is required")
        String animalType,
        
        @NotBlank(message = "Breed is required")
        String breed,
        
        @NotNull(message = "Age is required")
        @Min(value = 0, message = "Age cannot be negative")
        Integer age,
        
        @NotBlank(message = "Eircode is required")
        String eircode
) {}