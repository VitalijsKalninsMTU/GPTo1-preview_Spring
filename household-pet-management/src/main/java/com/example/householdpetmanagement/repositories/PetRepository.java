package com.example.householdpetmanagement.repositories;

import com.example.householdpetmanagement.entities.Pet;
import com.example.householdpetmanagement.dtos.PetNameTypeBreedDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Long> {

    // Find pets by animal type
    List<Pet> findByAnimalTypeIgnoreCase(String animalType);

    // Find pets by breed, ordered by age ascending
    List<Pet> findByBreedOrderByAgeAsc(String breed);

    // Delete pets by name ignoring case
    void deleteByNameIgnoreCase(String name);

    // Return a list of records consisting of name, animal type and breed
    @Query("SELECT p.name as name, p.animalType as animalType, p.breed as breed FROM Pet p")
    List<PetNameTypeBreedDto> findNameAnimalTypeAndBreed();

    // For Pet Statistics: Average age, oldest age, total count
    @Query("SELECT AVG(p.age) FROM Pet p")
    Double findAverageAge();

    @Query("SELECT MAX(p.age) FROM Pet p")
    Integer findOldestAge();

    // Count is provided by JpaRepository as count()
}