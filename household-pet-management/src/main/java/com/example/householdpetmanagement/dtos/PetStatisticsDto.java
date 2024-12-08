package com.example.householdpetmanagement.dtos;

public class PetStatisticsDto {
    private Double averageAge;
    private Integer oldestAge;
    private Long totalCount;

    public PetStatisticsDto(Double averageAge, Integer oldestAge, Long totalCount) {
        this.averageAge = averageAge;
        this.oldestAge = oldestAge;
        this.totalCount = totalCount;
    }
    
    // Getters and Setters
    public Double getAverageAge() {
        return averageAge;
    }

    public void setAverageAge(Double averageAge) {
        this.averageAge = averageAge;
    }

    public Integer getOldestAge() {
        return oldestAge;
    }

    public void setOldestAge(Integer oldestAge) {
        this.oldestAge = oldestAge;
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Long totalCount) {
        this.totalCount = totalCount;
    }
}