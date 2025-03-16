package com.galaxycodes.springsecurity.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdatePatientDTO(
    String email,
    String address,
    String firstName,
    String lastName,
    String phoneNumber,
    String bloodType,
    String age,
    String bloodPressure,
    Integer height,
    Integer weight,
    String sex,
    Integer temperature,
    Integer doctorID,
    Integer hospitalID,
    Integer patientID

) {
}
