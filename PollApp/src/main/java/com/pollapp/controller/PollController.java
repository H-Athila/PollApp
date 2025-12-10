package com.pollapp.controller;

import com.pollapp.model.Poll;
import com.pollapp.model.Participant;
import com.pollapp.model.User;
import com.pollapp.service.PollManagementService;
import com.pollapp.service.VotingService;
import com.pollapp.service.UserService; // Using UserService for finding user ID
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/polls")
public class PollController {

    private final PollManagementService pollManagementService;
    private final VotingService votingService;
    private final UserService userService; 

    public PollController(PollManagementService pollManagementService, VotingService votingService, UserService userService) {
        this.pollManagementService = pollManagementService;
        this.votingService = votingService;
        this.userService = userService;
    }
    
    // Redirects the base /vote URL (used in SecurityConfig) to the poll list.
    @GetMapping("/vote")
    public String redirectToPollList() {
        return "redirect:/polls/list";
    }

    /**
     * Shows a list of all available polls.
     */
    @GetMapping("/list")
    public String listPolls(Model model) {
        List<Poll> polls = pollManagementService.getAllPolls();
        model.addAttribute("polls", polls);
        return "poll/list"; 
    }

    /**
     * Shows the form to create a new poll.
     */
    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("poll", new Poll());
        return "poll/create"; 
    }

    /**
     * Handles the form submission to create a new poll.
     */
    @PostMapping("/create")
    public String createPoll(@ModelAttribute Poll poll, 
                             @RequestParam("participantName") List<String> participantNames, // Matches the input names in HTML
                             @AuthenticationPrincipal UserDetails userDetails) {
        
        // Get the creator's ID
        User creator = userService.findByUsername(userDetails.getUsername());
        
        pollManagementService.createNewPoll(poll, participantNames, creator.getId());
        
        return "redirect:/polls/list?created";
    }

    /**
     * Shows the voting page for a specific poll ID.
     */
    @GetMapping("/vote/{pollId}")
    public String showVotingPage(@PathVariable Long pollId, Model model, @AuthenticationPrincipal UserDetails userDetails) {
        User user = userService.findByUsername(userDetails.getUsername());
        
        Poll poll = pollManagementService.getPollById(pollId);
        boolean hasVoted = votingService.hasUserVoted(user.getId(), pollId);
        
        model.addAttribute("poll", poll);
        // Note: The Poll object already contains the participants list
        model.addAttribute("hasVoted", hasVoted);
        
        return "poll/vote";
    }
    
    /**
     * Handles the vote submission for a specific poll ID.
     */
    @PostMapping("/submit/{pollId}")
    public String submitVote(@PathVariable Long pollId, 
                             @RequestParam Long participantId, // ID of the selected participant
                             @AuthenticationPrincipal UserDetails userDetails) {
        
        User user = userService.findByUsername(userDetails.getUsername());
        
        // Cast the vote using the user's ID, participant ID, and the poll ID
        boolean success = votingService.castVote(user.getId(), participantId, pollId);

        if (success) {
            return "redirect:/polls/results/" + pollId + "?voted";
        } else {
            return "redirect:/polls/vote/" + pollId + "?error=voted_already";
        }
    }
    
    /**
     * Shows the results for a specific poll ID.
     */
    @GetMapping("/results/{pollId}")
    public String showResults(@PathVariable Long pollId, Model model) {
        Poll poll = pollManagementService.getPollById(pollId);
        // Get sorted results from the service
        List<Participant> results = votingService.getVotingResults(pollId);
        
        model.addAttribute("poll", poll);
        model.addAttribute("results", results);
        
        return "poll/results";
    }
}