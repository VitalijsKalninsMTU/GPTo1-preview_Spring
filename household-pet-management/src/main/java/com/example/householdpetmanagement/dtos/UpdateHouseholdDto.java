package com.example.householdpetmanagement.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateHouseholdDto(
        @NotNull(message = "Number of occupants is required")
        @Min(value = 0, message = "Number of occupants cannot be negative")
        Integer numberOfOccupants,

        @NotNull(message = "Max number of occupants is required")
        @Min(value = 0, message = "Max number of occupants cannot be negative")
        Integer maxNumberOfOccupants,

        @NotNull(message = "Owner occupied is required")
        Boolean ownerOccupied
) {}