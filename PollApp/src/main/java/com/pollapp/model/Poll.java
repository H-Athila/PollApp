package com.pollapp.model; // <<< Uses the correct base package name

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "polls") // Maps to the 'polls' table in the database
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // The question the user asks (e.g., "What is your favorite color?")
    private String question; 

    // Links the poll to the user who created it
    @Column(name = "created_by_user_id")
    private Long createdByUserId; 
    
    // Timestamp for when the poll was created
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now(); 

    // One Poll can have many Participants (choices)
    // CascadeType.ALL ensures that if the Poll is deleted, its Participants are deleted too.
    @OneToMany(mappedBy = "poll", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Participant> participants; // Note: Uses the renamed Participant list

    // --- Constructor, Getters, and Setters ---
    public Poll() {}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getQuestion() { return question; }
    public void setQuestion(String question) { this.question = question; }
    public Long getCreatedByUserId() { return createdByUserId; }
    public void setCreatedByUserId(Long createdByUserId) { this.createdByUserId = createdByUserId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    
    // Getter/Setter updated to use the 'participants' list name
    public List<Participant> getParticipants() { return participants; } 
    public void setParticipants(List<Participant> participants) { this.participants = participants; }
}