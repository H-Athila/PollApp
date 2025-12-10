package com.pollapp.service;

import com.pollapp.model.Poll;
import com.pollapp.model.Participant;
import com.pollapp.repository.PollRepository;
import com.pollapp.repository.ParticipantRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class PollManagementService {

    private final PollRepository pollRepository;
    private final ParticipantRepository participantRepository;

    public PollManagementService(PollRepository pollRepository, ParticipantRepository participantRepository) {
        this.pollRepository = pollRepository;
        this.participantRepository = participantRepository;
    }

    /**
     * Saves a new poll and its participant options.
     */
    @Transactional
    public Poll createNewPoll(Poll poll, List<String> participantNames, Long creatorId) {
        poll.setCreatedByUserId(creatorId);
        
        // 1. Save the main Poll object first
        Poll savedPoll = pollRepository.save(poll);

        // 2. Create and save the associated Participants
        for (String name : participantNames) {
            if (name != null && !name.trim().isEmpty()) {
                // Use the simplified Participant constructor (name, poll)
                Participant participant = new Participant(name, savedPoll);
                participantRepository.save(participant);
            }
        }
        return savedPoll;
    }

    /**
     * Retrieves all available polls.
     */
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }
    
    /**
     * Retrieves a single poll by ID, throwing an exception if not found.
     */
    public Poll getPollById(Long pollId) {
        return pollRepository.findById(pollId)
               .orElseThrow(() -> new RuntimeException("Poll not found with ID: " + pollId));
    }
}