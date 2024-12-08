package com.example.householdpetmanagement.dtos;

import com.example.householdpetmanagement.entities.Pet;

import java.util.List;

public class HouseholdWithPetsDto {

    private String eircode;
    private int numberOfOccupants;
    private int maxNumberOfOccupants;
    private boolean ownerOccupied;
    private List<Pet> pets;

    public HouseholdWithPetsDto(String eircode, int numberOfOccupants, int maxNumberOfOccupants, boolean ownerOccupied, List<Pet> pets) {
        this.eircode = eircode;
        this.numberOfOccupants = numberOfOccupants;
        this.maxNumberOfOccupants = maxNumberOfOccupants;
        this.ownerOccupied = ownerOccupied;
        this.pets = pets;
    }

    public String getEircode() {
        return eircode;
    }

    public int getNumberOfOccupants() {
        return numberOfOccupants;
    }

    public int getMaxNumberOfOccupants() {
        return maxNumberOfOccupants;
    }

    public boolean isOwnerOccupied() {
        return ownerOccupied;
    }

    public List<Pet> getPets() {
        return pets;
    }
}