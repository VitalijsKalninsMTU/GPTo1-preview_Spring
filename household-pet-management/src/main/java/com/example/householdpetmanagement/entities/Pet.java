package com.example.householdpetmanagement.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "pets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "animal_type", nullable = false, length = 50)
    private String animalType;

    @Column(name = "breed", nullable = false, length = 100)
    private String breed;

    @Column(name = "age", nullable = false)
    private int age;

    // Many pets can belong to one household
    @ManyToOne
    @JoinColumn(name = "eircode", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private Household household;
}