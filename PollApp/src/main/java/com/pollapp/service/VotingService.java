package com.pollapp.service;

import com.pollapp.model.Participant;
import com.pollapp.model.Vote;
import com.pollapp.repository.ParticipantRepository; 
import com.pollapp.repository.VoteRepository;     
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class VotingService {

    private final ParticipantRepository participantRepository;
    private final VoteRepository voteRepository;

    public VotingService(ParticipantRepository participantRepository, VoteRepository voteRepository) {
        this.participantRepository = participantRepository;
        this.voteRepository = voteRepository;
    }

    /**
     * Checks if a user has voted in a SPECIFIC poll.
     */
    public boolean hasUserVoted(Long userId, Long pollId) { 
        return voteRepository.existsByUserIdAndPollId(userId, pollId);
    }

    /**
     * Handles casting a vote for a SPECIFIC poll.
     */
    @Transactional 
    public boolean castVote(Long userId, Long participantId, Long pollId) { 
        if (hasUserVoted(userId, pollId)) {
            return false; // User has already voted in this poll
        }

        Optional<Participant> participantOpt = participantRepository.findById(participantId);
        
        // Safety check: Does the participant exist AND does it belong to the poll the user thinks they are voting in?
        if (participantOpt.isEmpty() || !participantOpt.get().getPoll().getId().equals(pollId)) {
            return false; 
        }
        Participant participant = participantOpt.get();

        // 1. Increment the vote count
        participant.setVoteCount(participant.getVoteCount() + 1);
        participantRepository.save(participant);

        // 2. Record the vote
        Vote vote = new Vote(userId, participantId, pollId); 
        voteRepository.save(vote);

        return true;
    }

    /**
     * Gets results (list of Participants ordered by votes) for a SPECIFIC poll.
     */
    public List<Participant> getVotingResults(Long pollId) { 
        // Uses the custom method defined in ParticipantRepository
        return participantRepository.findByPollIdOrderByVoteCountDesc(pollId);
    }
}