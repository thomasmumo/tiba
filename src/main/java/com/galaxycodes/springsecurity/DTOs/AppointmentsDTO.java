package com.galaxycodes.springsecurity.DTOs;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentsDTO(
        @NotNull(message = "Appointment reason is required") @Size(min = 1,message = "Appointment reason is required")
        String appointmentReason,
        @NotNull(message = "Date is required")
        String appointmentDate,
        @NotNull(message = "Appointment time is required")
        String appointmentTime,
        @NotNull(message = "patient id is required")
        Integer patientID,
        @NotNull(message = "hospital id is required")
        Integer hospitalID,
        Integer doctorID

) {
}
