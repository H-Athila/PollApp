package com.pollapp.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "participants") // <<< New table name
public class Participant {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // The poll option text
    
    @Column(name = "vote_count")
    private int voteCount = 0;
    
    // Link back to the Poll
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "poll_id", nullable = false)
    @JsonIgnore
    private Poll poll; 
    
    // --- Constructors ---
    public Participant() {}

    public Participant(String name, Poll poll) {
        this.name = name;
        this.poll = poll; 
    }

    // --- Getters and Setters ---
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public int getVoteCount() { return voteCount; }
    public void setVoteCount(int voteCount) { this.voteCount = voteCount; }
    
    public Poll getPoll() { return poll; }
    public void setPoll(Poll poll) { this.poll = poll; }
}
