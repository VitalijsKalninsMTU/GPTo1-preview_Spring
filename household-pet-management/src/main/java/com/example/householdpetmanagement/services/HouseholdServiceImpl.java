package com.example.householdpetmanagement.services;

import com.example.householdpetmanagement.dtos.HouseholdStatisticsDto;
import com.example.householdpetmanagement.dtos.HouseholdWithPetsDto;
import com.example.householdpetmanagement.dtos.HouseholdWithoutPetsDto;
import com.example.householdpetmanagement.entities.Household;
import com.example.householdpetmanagement.exceptions.BadDataException;
import com.example.householdpetmanagement.exceptions.NotFoundException;
import com.example.householdpetmanagement.repositories.HouseholdRepository;
import com.example.householdpetmanagement.repositories.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class HouseholdServiceImpl implements HouseholdService {

    private final HouseholdRepository householdRepository;
    private final PetRepository petRepository;

    public HouseholdServiceImpl(HouseholdRepository householdRepository, PetRepository petRepository) {
        this.householdRepository = householdRepository;
        this.petRepository = petRepository;
    }

    @Override
    public Household createHousehold(Household household) {
        if (household == null 
                || household.getEircode() == null 
                || household.getEircode().trim().isEmpty()
                || household.getMaxNumberOfOccupants() < 0
                || household.getNumberOfOccupants() < 0
                || household.getNumberOfOccupants() > household.getMaxNumberOfOccupants()) {
            throw new BadDataException("Invalid household data.");
        }
        return householdRepository.save(household);
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public HouseholdWithoutPetsDto getHouseholdByIdWithoutPets(String eircode) {
        Household h = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
        return new HouseholdWithoutPetsDto(
                h.getEircode(),
                h.getNumberOfOccupants(),
                h.getMaxNumberOfOccupants(),
                h.isOwnerOccupied()
        );
    }

    @Override
    public HouseholdWithPetsDto getHouseholdByIdWithPets(String eircode) {
        // Use the custom query that fetches pets eagerly
        Household h = householdRepository.findByEircodeWithPets(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));
        return new HouseholdWithPetsDto(
                h.getEircode(),
                h.getNumberOfOccupants(),
                h.getMaxNumberOfOccupants(),
                h.isOwnerOccupied(),
                h.getPets() // Pets are already loaded due to JOIN FETCH
        );
    }

    @Override
    public Household updateHousehold(String eircode, Household updatedHousehold) {
        Household existingHousehold = householdRepository.findById(eircode)
                .orElseThrow(() -> new NotFoundException("Household not found with eircode: " + eircode));

        if (updatedHousehold.getNumberOfOccupants() < 0 
                || updatedHousehold.getMaxNumberOfOccupants() < 0
                || updatedHousehold.getNumberOfOccupants() > updatedHousehold.getMaxNumberOfOccupants()) {
            throw new BadDataException("Invalid occupant data.");
        }

        existingHousehold.setNumberOfOccupants(updatedHousehold.getNumberOfOccupants());
        existingHousehold.setMaxNumberOfOccupants(updatedHousehold.getMaxNumberOfOccupants());
        existingHousehold.setOwnerOccupied(updatedHousehold.isOwnerOccupied());

        return householdRepository.save(existingHousehold);
    }

    @Override
    public void deleteHouseholdById(String eircode) {
        if (!householdRepository.existsById(eircode)) {
            throw new NotFoundException("Household not found with eircode: " + eircode);
        }
        householdRepository.deleteById(eircode);
    }

    @Override
    public void deletePetsByNameIgnoringCase(String name) {
        petRepository.deleteByNameIgnoreCase(name);
    }

    @Override
    public List<Household> findHouseholdsWithNoPets() {
        return householdRepository.findHouseholdsWithNoPets();
    }

    @Override
    public List<Household> findOwnerOccupiedHouseholds() {
        return householdRepository.findByOwnerOccupiedTrue();
    }

    @Override
    public HouseholdStatisticsDto getHouseholdStatistics() {
        Long emptyCount = householdRepository.countEmptyHouses();
        Long fullCount = householdRepository.countFullHouses();

        return new HouseholdStatisticsDto(emptyCount, fullCount);
    }
}