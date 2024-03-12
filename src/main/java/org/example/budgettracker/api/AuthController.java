package org.example.budgettracker.api;

import lombok.RequiredArgsConstructor;
import org.example.budgettracker.model.request.AuthRequest;
import org.example.budgettracker.model.response.AuthResponse;
import org.example.budgettracker.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest registrationRequest) {
        try {
            return ResponseEntity.ok(authService.register(registrationRequest));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        try {
            return ResponseEntity.ok(authService.loginUser(authRequest.username(), authRequest.password()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}
