package com.galaxycodes.springsecurity.DTOs;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;



public record PatientsDTO(
        @NotNull(message = "username is required")@Size(min = 1,message = "username is required")
         String userName,
         @NotNull(message = "password is required")@Size(min = 8,message = "password must be 8 or more characters")
         String password,
         @Email(message = "invalid email")
         String email,
         @NotNull(message = "address is required")@Size(min = 1,message = "address is required")
         String address,
         @NotNull(message = "first name is required")@Size(min = 1,message = "first name is required")
         String firstName,
         @NotNull(message = "last name is required")@Size(min = 1,message = "last name is required")
        String lastName,
        Integer hospitalId,
        @NotNull(message = "phone number is required")@Size(min = 10,max = 10,message = "phone number must have a maximum and minimum of 10 characters")
        String phoneNumber

) {
}
