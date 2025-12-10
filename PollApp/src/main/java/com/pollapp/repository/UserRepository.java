package com.pollapp.repository;

import com.pollapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * Custom method used by CustomUserDetailsService to load a user for authentication.
     */
    User findByUsername(String username);
}