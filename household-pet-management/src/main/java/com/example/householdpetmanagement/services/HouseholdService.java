package com.example.householdpetmanagement.services;

import com.example.householdpetmanagement.dtos.HouseholdStatisticsDto;
import com.example.householdpetmanagement.dtos.HouseholdWithPetsDto;
import com.example.householdpetmanagement.dtos.HouseholdWithoutPetsDto;
import com.example.householdpetmanagement.entities.Household;

import java.util.List;

public interface HouseholdService {
    Household createHousehold(Household household);

    List<Household> getAllHouseholds();

    HouseholdWithoutPetsDto getHouseholdByIdWithoutPets(String eircode);

    HouseholdWithPetsDto getHouseholdByIdWithPets(String eircode);

    Household updateHousehold(String eircode, Household updatedHousehold);

    void deleteHouseholdById(String eircode);

    void deletePetsByNameIgnoringCase(String name);

    List<Household> findHouseholdsWithNoPets();

    List<Household> findOwnerOccupiedHouseholds();

    HouseholdStatisticsDto getHouseholdStatistics();
}