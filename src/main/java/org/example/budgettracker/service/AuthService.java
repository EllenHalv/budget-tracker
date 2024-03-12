package org.example.budgettracker.service;


import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.request.AuthRequest;
import org.example.budgettracker.model.response.AuthResponse;
import org.example.budgettracker.repository.RoleRepository;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final RoleRepository roleRepo;

    public AuthResponse register(AuthRequest request) {
        if (userRepo.findByUsername(request.username()).isPresent())
            throw new RuntimeException("Username is taken");
        if (request.username() == null || request.username().isEmpty())
            throw new RuntimeException("Username cannot be empty");
        if (request.password() == null || request.password().isEmpty())
            throw new RuntimeException("Password cannot be empty");

        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(new HashSet<>(List.of(roleRepo.findByAuthority("USER").orElseThrow(() -> new RuntimeException("User role not found")))))
                .budgets(new ArrayList<>())
                .build();

        userRepo.save(user);
        return new AuthResponse(user, tokenService.generateJwt(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())));
    }

    public AuthResponse loginUser(String username, String password) {
        Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        String token = tokenService.generateJwt(auth);
        User user = userRepo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        return new AuthResponse(user, token);
    }
}