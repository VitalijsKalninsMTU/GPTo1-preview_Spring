package com.example.householdpetmanagement.controllers;

import com.example.householdpetmanagement.dtos.*;
import com.example.householdpetmanagement.entities.Household;
import com.example.householdpetmanagement.services.HouseholdService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/households")
public class HouseholdController {

    private final HouseholdService householdService;

    public HouseholdController(HouseholdService householdService) {
        this.householdService = householdService;
    }

    // 1. Create Household
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Household createHousehold(@Valid @RequestBody CreateHouseholdDto dto) {
        Household h = new Household();
        h.setEircode(dto.eircode());
        h.setNumberOfOccupants(dto.numberOfOccupants());
        h.setMaxNumberOfOccupants(dto.maxNumberOfOccupants());
        h.setOwnerOccupied(dto.ownerOccupied());
        return householdService.createHousehold(h);
    }

    // 2. Read All households
    @GetMapping
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    // 3. Read Household by ID - no pets details
    @GetMapping("/{eircode}/no-pets")
    public HouseholdWithoutPetsDto getHouseholdByIdWithoutPets(@PathVariable String eircode) {
        return householdService.getHouseholdByIdWithoutPets(eircode);
    }

    // 4. Read Household by ID - with pets details
    @GetMapping("/{eircode}/with-pets")
    public HouseholdWithPetsDto getHouseholdByIdWithPets(@PathVariable String eircode) {
        return householdService.getHouseholdByIdWithPets(eircode);
    }

    // 5. Update Household Details
    @PutMapping("/{eircode}")
    public Household updateHousehold(@PathVariable String eircode, @Valid @RequestBody UpdateHouseholdDto dto) {
        Household h = new Household();
        // eircode remains the same, we are just updating details
        h.setNumberOfOccupants(dto.numberOfOccupants());
        h.setMaxNumberOfOccupants(dto.maxNumberOfOccupants());
        h.setOwnerOccupied(dto.ownerOccupied());
        return householdService.updateHousehold(eircode, h);
    }

    // 6. Delete Household by ID
    @DeleteMapping("/{eircode}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteHousehold(@PathVariable String eircode) {
        householdService.deleteHouseholdById(eircode);
    }

    // 7. Delete Pets by Name (triggered from household perspective, but uses pet service)
    @DeleteMapping("/pets/by-name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetsByName(@PathVariable String name) {
        householdService.deletePetsByNameIgnoringCase(name);
    }

    // 8. Find Households with no pets
    @GetMapping("/no-pets")
    public List<Household> findHouseholdsWithNoPets() {
        return householdService.findHouseholdsWithNoPets();
    }

    // 9. Find Households that are owner-occupied
    @GetMapping("/owner-occupied")
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdService.findOwnerOccupiedHouseholds();
    }

    // 10. Get Household Statistics
    @GetMapping("/statistics")
    public HouseholdStatisticsDto getHouseholdStatistics() {
        return householdService.getHouseholdStatistics();
    }
}