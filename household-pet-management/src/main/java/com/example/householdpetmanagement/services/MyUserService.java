package com.example.householdpetmanagement.services;

import com.example.householdpetmanagement.entities.MyUser;
import com.example.householdpetmanagement.exceptions.UserNameNotFoundException;
import com.example.householdpetmanagement.repositories.MyUserRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    private final MyUserRepository userRepository;

    public MyUserService(MyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UserNameNotFoundException {
        MyUser foundUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNameNotFoundException("User not found: " + username));

        // Construct a UserDetails object. 
        // locked = !unlocked, enabled = true, credentialsNonExpired = true, accountNonExpired = true
        boolean accountNonLocked = foundUser.isUnlocked();
        return User.builder()
                .username(foundUser.getUsername())
                .password(foundUser.getPassword())
                .roles(foundUser.getRole()) // If using roles, ensure they start with "ROLE_"
                .accountLocked(!accountNonLocked)
                .build();
    }
}