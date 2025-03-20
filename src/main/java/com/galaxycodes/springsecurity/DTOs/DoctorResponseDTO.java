package com.galaxycodes.springsecurity.DTOs;

public record DoctorResponseDTO(
        Integer id,
        String firstname,
        String lastname,
        String username
) {
}
