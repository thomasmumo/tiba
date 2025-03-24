package com.galaxycodes.springsecurity.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public record StaffManagementResponseDTO(
        LocalDate date,
        Integer id,
        LocalTime loggedInTime,
        LocalTime loggedOutTime,
        String shift,
        UpdateUserDTO doctor,
        Float workHours
) {
}
