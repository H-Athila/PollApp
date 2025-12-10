package com.pollapp.service; // <<< Uses the correct package name

import com.pollapp.model.User;
import com.pollapp.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Saves a new user to the database, ensuring the password is BCrypt encoded.
     */
    public User saveUser(User user) {
        // CRITICAL: Encode the plaintext password before saving
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        
        return userRepository.save(user);
    }
    
    /**
     * Finds a user by username. Used by controllers (e.g., PollController) to get the user's ID.
     */
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}