package com.example.householdpetmanagement.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.householdpetmanagement.entities.MyUser;
import java.util.Optional;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    Optional<MyUser> findByUsername(String username);
}









