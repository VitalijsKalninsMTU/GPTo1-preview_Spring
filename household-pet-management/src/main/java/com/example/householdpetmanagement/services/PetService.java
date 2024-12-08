package com.example.householdpetmanagement.services;

import com.example.householdpetmanagement.entities.Pet;
import com.example.householdpetmanagement.dtos.PetNameTypeBreedDto;
import com.example.householdpetmanagement.dtos.PetStatisticsDto;

import java.util.List;

public interface PetService {

    Pet createPet(Pet pet);

    List<Pet> getAllPets();

    Pet getPetById(Long id);

    Pet updatePet(Long id, Pet updatedPet); // Add validations/checks at the service layer

    void deletePetById(Long id);

    void deletePetsByNameIgnoringCase(String name);

    List<Pet> findPetsByAnimalType(String animalType);

    List<Pet> findPetsByBreed(String breed);

    List<PetNameTypeBreedDto> getNameAnimalTypeAndBreed();

    PetStatisticsDto getPetStatistics();
}