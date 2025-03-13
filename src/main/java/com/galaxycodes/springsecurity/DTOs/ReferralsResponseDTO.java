package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Patients;

import java.time.LocalDate;

public record ReferralsResponseDTO(
        HospitalDTO fromHospital,
        Integer toHospitalID,
        UsersResponseDTO fromDoctor,
        Integer toDoctorID,
        String referralReason,
        Patients patient,
        String status,
        LocalDate referralDate
) {
}
