package com.galaxycodes.springsecurity.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateUserDTO(

        String firstName,

        String lastName,

        String userName,
        String role,


        Integer phoneNumber,


        String email
) {
}
