package org.example.budgettracker.api;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.entity.User;
import org.example.budgettracker.model.request.NewPasswordRequest;
import org.example.budgettracker.model.request.NewUsernameRequest;
import org.example.budgettracker.model.request.RegisterRequest;
import org.example.budgettracker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

/**
 * Class for a logged in User to Update, Delete their User data
 */

@CrossOrigin("*")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // user gets user-info (username, dolt lösen, kan användas till user-page senare om user entity ska vidareutvecklas)
    @GetMapping("/{id}")
    public ResponseEntity<User> selectOneById(
            Authentication auth,
            @PathVariable Long id
    ) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!isLoggedInUser(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            return ResponseEntity.ok(userService.findById(id));
        } catch (NoResultException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    //admin skapar användare
    @PostMapping
    public ResponseEntity<String> create(
            Authentication authentication,
            @RequestBody RegisterRequest registerRequest
    ) {
        if(authentication == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(!isAdmin(authentication)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            userService.create(registerRequest);
            return ResponseEntity.ok("1");
        } catch (Exception e) {
            return ResponseEntity.ok("0");
        }
    }

    //user updates username
    @PutMapping("/username/{id}")
    public ResponseEntity<String> updateUsername(
            Authentication auth,
            @RequestBody NewUsernameRequest request
    ) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(isLoggedInUser(request.id(), auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            userService.updateUsername(request);
            return ResponseEntity.ok("1");
        } catch (Exception e) {
            return ResponseEntity.ok("0");
        }
    }

    //user updates password
    @PutMapping("/password/{id}")
    public ResponseEntity<String> updatePassword(
            Authentication auth,
            @RequestBody NewPasswordRequest request
    ) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(isLoggedInUser(request.id(), auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            userService.updatePassword(request);
            return ResponseEntity.ok("1");
        } catch (Exception e) {
            return ResponseEntity.ok("0");
        }
    }

    //user deletes account
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(
            Authentication auth,
            @PathVariable Long id
    ) {
        if(auth == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        if(isLoggedInUser(id, auth)) return ResponseEntity.status(HttpStatus.FORBIDDEN).build();

        try {
            userService.deleteById(id);
            return ResponseEntity.status(204).build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // check if user is logged in user
    private boolean isLoggedInUser(Long id, Authentication auth) {
        return userService.findById(id).equals(auth.getName());
    }

    private boolean isAdminOrLoggedInUser(Long id, Authentication authentication) {
        return isAdmin(authentication) || isLoggedInUser(id, authentication);
    }
    private boolean isAdmin(Authentication authentication) {
        for(GrantedAuthority authority : authentication.getAuthorities()) {
            if(authority.getAuthority().equals("ROLE_ADMIN")) {
                return true;
            }
        }
        return false;
    }
}
