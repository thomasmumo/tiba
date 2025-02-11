package com.galaxycodes.springsecurity.DTOs;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UsersDTO(
        @NotNull(message = "first name is required")
        @Size(min = 1,message = "first name is required")
        String firstName,
        @NotNull(message = "last name is required")
        @Size(min = 1,message = "last name is required")
        String lastName,
        @NotNull(message = "username is required")
        @Size(min = 1,message = "username is required")
        String userName,
        @Size(min = 8, message = "password must be 8 or more characters")
        String password,

        String role,
        @NotNull( message = "phone number is required")

        Integer phoneNumber,
        @NotNull(message = "Hospital ID is required")
        Integer hospitalId,
        @Email(message = "not a valid email")
        String email
) {
}
