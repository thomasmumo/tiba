package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;

public record MedicalRecordsResponseDTO(
        Integer id,
        String conclusion,
        String symptoms,
        Users user,
        Hospitals hospital,
        Patients patient
) {
}
