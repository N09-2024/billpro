package com.example.billpro.dto;

public record PasswordChangeRequest(
        String oldPassword,
        String newPassword,
        String confirmPassword) {
}
