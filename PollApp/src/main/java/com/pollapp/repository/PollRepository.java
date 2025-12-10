package com.pollapp.repository;

import com.pollapp.model.Poll;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PollRepository extends JpaRepository<Poll, Long> {
    
    // No custom methods needed here yet, but we will use the built-in findAll() and findById().
}