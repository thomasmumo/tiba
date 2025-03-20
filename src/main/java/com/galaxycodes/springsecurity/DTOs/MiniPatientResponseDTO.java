package com.galaxycodes.springsecurity.DTOs;

public record MiniPatientResponseDTO(
        Integer id,
        String firstname,
        String lastname,
        String username
) {
}
