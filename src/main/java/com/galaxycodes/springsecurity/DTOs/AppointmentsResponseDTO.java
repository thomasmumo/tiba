package com.galaxycodes.springsecurity.DTOs;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentsResponseDTO(
        LocalDate appointmentDate,
        String appointmentReason,
        String appointmentStatus,
        LocalTime appointmentTime,
        HospitalResponseDTO hospital,
        MiniPatientResponseDTO patient,
        DoctorResponseDTO doctor
) {
}
