package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;

import java.time.LocalDate;

public record MedicalRecordsResponseDTO(
        Integer id,
        LocalDate date,
        String conclusion,
        String symptoms,
        UsersResponseDTO user,
        HospitalDTO hospital,
        Patients patient
) {
}
