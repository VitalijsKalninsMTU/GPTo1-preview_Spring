package com.example.householdpetmanagement.controllers;

import com.example.householdpetmanagement.dtos.CreatePetDto;
import com.example.householdpetmanagement.dtos.HouseholdWithPetsDto;
import com.example.householdpetmanagement.dtos.PetNameTypeBreedDto;
import com.example.householdpetmanagement.dtos.PetStatisticsDto;
import com.example.householdpetmanagement.dtos.UpdatePetDto;
import com.example.householdpetmanagement.entities.Household;
import com.example.householdpetmanagement.entities.Pet;
import com.example.householdpetmanagement.services.HouseholdService;
import com.example.householdpetmanagement.services.PetService;
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
public class PetGraphQLController {

    private final PetService petService;
    private final HouseholdService householdService;

    public PetGraphQLController(PetService petService, HouseholdService householdService) {
        this.petService = petService;
        this.householdService = householdService;
    }

    @QueryMapping
    public List<Pet> pets() {
        return petService.getAllPets();
    }

    @QueryMapping
    public Pet petById(@Argument Long id) {
        return petService.getPetById(id);
    }

    @QueryMapping
    public List<Pet> petsByAnimalType(@Argument String animalType) {
        return petService.findPetsByAnimalType(animalType);
    }

    @QueryMapping
    public List<Pet> petsByBreed(@Argument String breed) {
        return petService.findPetsByBreed(breed);
    }

    @QueryMapping
    public List<PetNameTypeBreedDto> petNameAnimalTypeBreed() {
        return petService.getNameAnimalTypeAndBreed();
    }

    @QueryMapping
    public PetStatisticsDto petStatistics() {
        return petService.getPetStatistics();
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can create pets
    public Pet createPet(@Argument @Valid CreatePetDto input) {
        Pet pet = new Pet();
        pet.setName(input.name());
        pet.setAnimalType(input.animalType());
        pet.setBreed(input.breed());
        pet.setAge(input.age());

        // fetch household from eircode (quick fix)
        HouseholdWithPetsDto houseData = householdService.getHouseholdByIdWithPets(input.eircode());
        Household household = new Household(houseData.getEircode(), houseData.getNumberOfOccupants(), houseData.getMaxNumberOfOccupants(), houseData.isOwnerOccupied(), houseData.getPets());

        pet.setHousehold(household);
        return petService.createPet(pet);
    }

    @MutationMapping
    @Secured({"ROLE_ADMIN","ROLE_USER"}) // Admin and User can update pets
    public Pet updatePet(@Argument Long id, @Argument @Valid UpdatePetDto input) {
        Pet updatedPet = new Pet();
        updatedPet.setName(input.name());
        updatedPet.setAge(input.age());
        updatedPet.setAnimalType(input.animalType());
        updatedPet.setBreed(input.breed());
        return petService.updatePet(id, updatedPet);
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can delete pets by ID
    public Boolean deletePetById(@Argument Long id) {
        petService.deletePetById(id);
        return true;
    }

    @MutationMapping
    @Secured("ROLE_ADMIN") // Only admin can delete pets by name
    public Boolean deletePetsByName(@Argument String name) {
        petService.deletePetsByNameIgnoringCase(name);
        return true;
    }
}