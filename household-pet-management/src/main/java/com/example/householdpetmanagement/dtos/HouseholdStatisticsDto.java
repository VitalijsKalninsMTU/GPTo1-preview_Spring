package com.example.householdpetmanagement.dtos;

public class HouseholdStatisticsDto {

    private Long numberOfEmptyHouses;
    private Long numberOfFullHouses;

    public HouseholdStatisticsDto(Long numberOfEmptyHouses, Long numberOfFullHouses) {
        this.numberOfEmptyHouses = numberOfEmptyHouses;
        this.numberOfFullHouses = numberOfFullHouses;
    }

    public Long getNumberOfEmptyHouses() {
        return numberOfEmptyHouses;
    }

    public void setNumberOfEmptyHouses(Long numberOfEmptyHouses) {
        this.numberOfEmptyHouses = numberOfEmptyHouses;
    }

    public Long getNumberOfFullHouses() {
        return numberOfFullHouses;
    }

    public void setNumberOfFullHouses(Long numberOfFullHouses) {
        this.numberOfFullHouses = numberOfFullHouses;
    }
}