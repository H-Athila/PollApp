package com.pollapp.model;

import jakarta.persistence.*;

@Entity
@Table(name = "votes", 
       uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "poll_id"})}
) 
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId; 
    
    @Column(name = "candidate_id") // Stores the Participant ID
    private Long candidateId;

    @Column(name = "poll_id", nullable = false)
    private Long pollId; 

    // --- Constructors ---
    public Vote() {}

    public Vote(Long userId, Long candidateId, Long pollId) { 
        this.userId = userId;
        this.candidateId = candidateId;
        this.pollId = pollId;
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public Long getCandidateId() { return candidateId; }
    public void setCandidateId(Long candidateId) { this.candidateId = candidateId; }
    
    public Long getPollId() { return pollId; }
    public void setPollId(Long pollId) { this.pollId = pollId; }
}