package com.example.householdpetmanagement.services;

import com.example.householdpetmanagement.dtos.PetNameTypeBreedDto;
import com.example.householdpetmanagement.dtos.PetStatisticsDto;
import com.example.householdpetmanagement.entities.Pet;
import com.example.householdpetmanagement.exceptions.BadDataException;
import com.example.householdpetmanagement.exceptions.NotFoundException;
import com.example.householdpetmanagement.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;

    public PetServiceImpl(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    @Override
    public Pet createPet(Pet pet) {
        if (pet == null || pet.getName() == null || pet.getAnimalType() == null || pet.getBreed() == null) {
            throw new BadDataException("Pet data is incomplete.");
        }
        return petRepository.save(pet);
    }

    @Override
    public List<Pet> getAllPets() {
        return petRepository.findAll();
    }

    @Override
    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + id));
    }

    @Override
    public Pet updatePet(Long id, Pet updatedPet) {
        Pet existingPet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Pet not found with ID: " + id));

        // Check the fields you want to update and ensure they are valid
        if (updatedPet.getName() == null || updatedPet.getName().trim().isEmpty()) {
            throw new BadDataException("Pet name cannot be empty.");
        }
        if (updatedPet.getAge() < 0) {
            throw new BadDataException("Pet age cannot be negative.");
        }

        existingPet.setName(updatedPet.getName());
        existingPet.setAge(updatedPet.getAge());
        existingPet.setAnimalType(updatedPet.getAnimalType() != null ? updatedPet.getAnimalType() : existingPet.getAnimalType());
        existingPet.setBreed(updatedPet.getBreed() != null ? updatedPet.getBreed() : existingPet.getBreed());
        // Household should remain the same or be verified if updating is allowed.

        return petRepository.save(existingPet);
    }

    @Override
    public void deletePetById(Long id) {
        if (!petRepository.existsById(id)) {
            throw new NotFoundException("Pet not found with ID: " + id);
        }
        petRepository.deleteById(id);
    }

    @Override
    public void deletePetsByNameIgnoringCase(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Pet> findPetsByAnimalType(String animalType) {
        return petRepository.findByAnimalTypeIgnoreCase(animalType);
    }

    @Override
    public List<Pet> findPetsByBreed(String breed) {
        return petRepository.findByBreedOrderByAgeAsc(breed);
    }

    @Override
    public List<PetNameTypeBreedDto> getNameAnimalTypeAndBreed() {
        return petRepository.findNameAnimalTypeAndBreed();
    }

    @Override
    public PetStatisticsDto getPetStatistics() {
        Double avgAge = petRepository.findAverageAge();
        Integer oldestAge = petRepository.findOldestAge();
        Long totalCount = petRepository.count();
        return new PetStatisticsDto(avgAge, oldestAge, totalCount);
    }
}