package org.example.budgettracker.service;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.request.NewPasswordRequest;
import org.example.budgettracker.model.request.NewUsernameRequest;
import org.example.budgettracker.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // find one user
    public User findById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        return userRepository.findById(id).orElseThrow(() -> new NoResultException("User not found"));
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
        userRepository.save(userToUpdate);
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
        userRepository.save(userToUpdate);
    }

    // delete user
    public void deleteById(Long id) {
        if (id == null || id < 1) throw new IllegalArgumentException("No valid id was found");
        userRepository.deleteById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
