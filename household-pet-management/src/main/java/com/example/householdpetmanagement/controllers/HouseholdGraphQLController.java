package com.example.householdpetmanagement.controllers;

import com.example.householdpetmanagement.dtos.*;
import com.example.householdpetmanagement.entities.Household;
import com.example.householdpetmanagement.services.HouseholdService;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.annotation.Secured;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.Valid;
import org.springframework.graphql.data.method.annotation.Argument;

import java.util.List;

@Controller
@Validated
public class HouseholdGraphQLController {

    private final HouseholdService householdService;

    public HouseholdGraphQLController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    @QueryMapping
    public List<Household> households() {
        return householdService.getAllHouseholds();
    }

    @QueryMapping
    public HouseholdWithoutPetsDto householdByIdWithoutPets(@Argument String eircode) {
        return householdService.getHouseholdByIdWithoutPets(eircode);
    }

    @QueryMapping
    public HouseholdWithPetsDto householdByIdWithPets(@Argument String eircode) {
        return householdService.getHouseholdByIdWithPets(eircode);
    }

    @QueryMapping
    public List<Household> householdsNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    @QueryMapping
    public List<Household> householdsOwnerOccupied() {
        return householdService.findOwnerOccupiedHouseholds();
    }

    @QueryMapping
    public HouseholdStatisticsDto householdStatistics() {
        return householdService.getHouseholdStatistics();
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can create households
    public Household createHousehold(@Argument @Valid CreateHouseholdDto input) {
        Household h = new Household();
        h.setEircode(input.eircode());
        h.setNumberOfOccupants(input.numberOfOccupants());
        h.setMaxNumberOfOccupants(input.maxNumberOfOccupants());
        h.setOwnerOccupied(input.ownerOccupied());
        return householdService.createHousehold(h);
    }

    @MutationMapping
    @Secured({"ROLE_ADMIN","ROLE_USER"}) // Admin or User can update households
    public Household updateHousehold(@Argument String eircode, @Argument @Valid UpdateHouseholdDto input) {
        Household h = new Household();
        h.setNumberOfOccupants(input.numberOfOccupants());
        h.setMaxNumberOfOccupants(input.maxNumberOfOccupants());
        h.setOwnerOccupied(input.ownerOccupied());
        return householdService.updateHousehold(eircode, h);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can delete households
    public Boolean deleteHousehold(@Argument String eircode) {
        householdService.deleteHouseholdById(eircode);
        return true;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can delete pets by name (from a household perspective)
    public Boolean deletePetsByNameFromHousehold(@Argument String name) {
        householdService.deletePetsByNameIgnoringCase(name);
        return true;
    }
}