package com.galaxycodes.springsecurity.DTOs;

public record PrescriptionDTO(
        String symptoms,
        String condition,
        String prescription,
        String complete
) {
}
