package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.Role;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.request.NewPasswordRequest;
import org.example.budgettracker.model.request.NewUsernameRequest;
import org.example.budgettracker.model.request.RegisterRequest;
import org.example.budgettracker.repository.RoleRepository;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder passwordEncoder;

    // find one user
    public User findById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        return userRepo.findById(id).orElseThrow(() -> new NoResultException("User not found"));
    }

    // admin creates a new user
    public void create(RegisterRequest registerRequest) {
        Set<Role> roles = registerRequest.roles().stream()
                .map(authority -> roleRepo.findByAuthority(authority).orElseGet(() -> new Role(authority)))
                .collect(Collectors.toSet());

        User user = User.builder()
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .roles(roles)
                .build();

        userRepo.save(user);
    }

    // update username
    public void updateUsername(NewUsernameRequest usernameRequest) {
        User userToUpdate = findById(usernameRequest.id());

        if (usernameRequest.id() == null || usernameRequest.id() < 1) {
            throw new IllegalArgumentException("No valid id was found");
        }
        if (usernameRequest.username() == null || usernameRequest.newUsername() == null) {
            throw new IllegalArgumentException("No username was found");
        }
        if (!userToUpdate.getUsername().equals(usernameRequest.username())) {
            throw new IllegalArgumentException("Username does not match");
        }
        userToUpdate.setUsername(usernameRequest.newUsername());
        userRepo.save(userToUpdate);
    }

    // update password
    public void updatePassword(NewPasswordRequest passwordRequest) {
        User userToUpdate = findById(passwordRequest.id());

        if (passwordRequest.id() == null || passwordRequest.id() < 1) {
            throw new IllegalArgumentException("No valid id was found");
        }
        if (passwordRequest.newPassword() == null || passwordRequest.newPassword().isEmpty()) {
            throw new IllegalArgumentException("No password was found");
        }
        userToUpdate.setPassword(passwordEncoder.encode(passwordRequest.newPassword()));
        userRepo.save(userToUpdate);
    }

    // delete user
    public void deleteById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        userRepo.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
