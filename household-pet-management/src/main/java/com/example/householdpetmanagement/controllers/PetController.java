package com.example.householdpetmanagement.controllers;

import com.example.householdpetmanagement.dtos.*;
import com.example.householdpetmanagement.entities.Household;
import com.example.householdpetmanagement.entities.Pet;
import com.example.householdpetmanagement.services.HouseholdService;
import com.example.householdpetmanagement.services.PetService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pets")
public class PetController {

    private final PetService petService;
    private final HouseholdService householdService;

    public PetController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    // 1. Create Pet
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Pet createPet(@Valid @RequestBody CreatePetDto dto) {
        Pet pet = new Pet();
        pet.setName(dto.name());
        pet.setAnimalType(dto.animalType());
        pet.setBreed(dto.breed());
        pet.setAge(dto.age());

        // fetch household from eircode (quick fix)
        HouseholdWithPetsDto houseData = householdService.getHouseholdByIdWithPets(dto.eircode());
        Household household = new Household(houseData.getEircode(), houseData.getNumberOfOccupants(), houseData.getMaxNumberOfOccupants(), houseData.isOwnerOccupied(), houseData.getPets());

        pet.setHousehold(household); 
        return petService.createPet(pet);
    }

    // 2. Read All Pets
    @GetMapping
    public List<Pet> getAllPets() {
        return petService.getAllPets();
    }

    // 3. Read Pet by ID
    @GetMapping("/{id}")
    public Pet getPetById(@PathVariable Long id) {
        return petService.getPetById(id);
    }

    // 4. Update Pet Details
    @PutMapping("/{id}")
    public Pet updatePet(@PathVariable Long id, @Valid @RequestBody UpdatePetDto dto) {
        Pet updatedPet = new Pet();
        updatedPet.setName(dto.name());
        updatedPet.setAge(dto.age());
        updatedPet.setAnimalType(dto.animalType());
        updatedPet.setBreed(dto.breed());
        return petService.updatePet(id, updatedPet);
    }

    // 5. Delete Pet by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetById(@PathVariable Long id) {
        petService.deletePetById(id);
    }

    // 6. Delete Pets by Name
    @DeleteMapping("/by-name/{name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePetsByName(@PathVariable String name) {
        petService.deletePetsByNameIgnoringCase(name);
    }

    // 7. Find Pets by Animal Type
    @GetMapping("/by-animal-type/{animalType}")
    public List<Pet> findPetsByAnimalType(@PathVariable String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    // 8. Find Pets by Breed
    @GetMapping("/by-breed/{breed}")
    public List<Pet> findPetsByBreed(@PathVariable String breed) {
        return petService.findPetsByBreed(breed);
    }

    // 9. Get name and breed only
    @GetMapping("/name-breed")
    public List<PetNameTypeBreedDto> getNameAnimalTypeAndBreed() {
        return petService.getNameAnimalTypeAndBreed();
    }

    // 10. Get Pet Statistics
    @GetMapping("/statistics")
    public PetStatisticsDto getPetStatistics() {
        return petService.getPetStatistics();
    }
}