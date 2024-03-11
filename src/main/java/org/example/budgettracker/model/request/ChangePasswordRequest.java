package org.example.budgettracker.model.request;

public record ChangePasswordRequest(Long id, String newPassword) {
}
