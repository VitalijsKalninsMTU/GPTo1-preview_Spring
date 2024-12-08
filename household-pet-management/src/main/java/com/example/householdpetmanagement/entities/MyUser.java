package com.example.householdpetmanagement.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password; // hashed password

    @Column(nullable = false)
    private String role; // e.g. ROLE_ADMIN, ROLE_USER

    @Column(nullable = false)
    private boolean unlocked; // true means account is unlocked

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private String county; // 'Cork', 'Kerry', etc.
}