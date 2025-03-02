package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Patients;

import java.util.List;

public record HospitalPatientsResponseDTO(
        Integer hospitalID,
        String hospitalName,
        List<Patients> patients
) {
}
