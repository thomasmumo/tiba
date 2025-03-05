package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;

import java.util.List;

public record HospitalPatientsResponseDTO(
        Integer hospitalID,
        String hospitalName,
        List<Patients> patients,
        List<Users> users
) {
}
