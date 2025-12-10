package com.pollapp.repository;

import com.pollapp.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
    
    /**
     * Checks if a vote already exists for a given user ID within a specific poll ID.
     * Used to enforce the "one vote per user per poll" rule.
     */
    boolean existsByUserIdAndPollId(Long userId, Long pollId);
}