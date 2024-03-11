package org.example.budgettracker.service;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Role;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.request.AuthRequest;
import org.example.budgettracker.model.response.AuthResponse;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthResponse register(AuthRequest request) {
        if (userRepository.findByUsername(request.username()).isPresent())
            throw new RuntimeException("Username is taken");
        if (request.username() == null || request.username().isEmpty())
            throw new RuntimeException("Username cannot be empty");
        if (request.password() == null || request.password().isEmpty())
            throw new RuntimeException("Password cannot be empty");

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .role(Role.USER)
                .budgets(new ArrayList<>())
                .build();

        userRepository.save(user);
        return new AuthResponse(user, tokenService.generateJwt(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())));
    }

    public AuthResponse loginUser(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = tokenService.generateJwt(auth);
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthResponse(user, token);
    }
}