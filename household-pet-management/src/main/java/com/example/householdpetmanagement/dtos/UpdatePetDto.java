package com.example.householdpetmanagement.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record UpdatePetDto(
        @NotBlank(message = "Name cannot be blank")
        String name,

        @Min(value = 0, message = "Age cannot be negative")
        Integer age,

        // Optional fields
        String animalType,
        String breed
) {}