package com.pollapp.controller;

import com.pollapp.model.User;
import com.pollapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.validation.Valid;

@Controller
public class SecurityController {

    private final UserService userService;

    public SecurityController(UserService userService) {
        this.userService = userService;
    }

    // Handles the root URL and redirects authenticated users to the poll list.
    @GetMapping("/")
    public String home() {
        return "home"; // home.html will handle navigation based on authentication status
    }

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            return "register";
        }
        
        // Check if username already exists
        if (userService.findByUsername(user.getUsername()) != null) {
            result.rejectValue("username", null, "Username is already taken.");
            return "register";
        }
        
        userService.saveUser(user);
        // Redirect to login page with a success parameter
        return "redirect:/login?registered"; 
    }
}