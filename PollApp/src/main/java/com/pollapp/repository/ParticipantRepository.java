package com.pollapp.repository;

import com.pollapp.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    
    /**
     * Retrieves all Participants belonging to a specific Poll, ordered by vote count (DESC).
     * This method is crucial for displaying poll results.
     */
    List<Participant> findByPollIdOrderByVoteCountDesc(Long pollId);
}