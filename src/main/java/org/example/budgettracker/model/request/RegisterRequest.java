package org.example.budgettracker.model.request;

import org.example.budgettracker.model.entity.Role;

public record RegisterRequest(String username, String password, Role role) {
}
