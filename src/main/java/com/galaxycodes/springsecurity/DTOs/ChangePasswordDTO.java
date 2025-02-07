package com.galaxycodes.springsecurity.DTOs;

import jakarta.validation.constraints.Size;

public record ChangePasswordDTO(
        String currentPassword,
        @Size(min = 8, message = "password must be 8 or more characters")
        String newPassword


) {
}
