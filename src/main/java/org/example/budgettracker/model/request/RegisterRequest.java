package org.example.budgettracker.model.request;

import org.example.budgettracker.model.entity.Role;

import java.util.Set;

public record RegisterRequest(String username, String password, Set<String> roles) {
}
