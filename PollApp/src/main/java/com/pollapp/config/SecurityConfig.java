package com.pollapp.config;

import com.pollapp.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }
    
    // Whitelist for public resources (available without login)
    private static final String[] PUBLIC_WHITELIST = {
        "/", "/register", "/login", "/css/*", "/js/*",
        "/v3/api-docs/**",     // Swagger/OpenAPI endpoints
        "/swagger-ui/**",
        "/swagger-ui.html"
    };
    
    // Define the password encoder bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        
        http
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(PUBLIC_WHITELIST).permitAll() // Allow public access to the whitelist
                .anyRequest().authenticated() // All other requests require authentication
            )
            // Configure Form-based Login
            .formLogin(form -> form
                .loginPage("/login")            
                .defaultSuccessUrl("/polls/list", true) // CRITICAL: Redirects logged-in users to the poll gallery
                .failureUrl("/login?error")     
                .permitAll()
            )
            // Configure Logout
            .logout(logout -> logout
                .logoutSuccessUrl("/")          // Redirects to the public home page on successful logout
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )
            // Link to your Custom User Details Service
            .userDetailsService(customUserDetailsService); 
            
        return http.build();
    }
}