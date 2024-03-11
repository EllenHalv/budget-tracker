package org.example.budgettracker.model.response;

import org.example.budgettracker.model.entity.User;

public record AuthResponse(User user, String jwt) {
}
