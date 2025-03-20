package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Patients;

import java.time.LocalDate;

public record UpdatedResponseDTO(
        Integer id,
        HospitalResponseDTO fromHospital,
        HospitalResponseDTO toHospitalID,
        DoctorResponseDTO fromDoctor,
        DoctorResponseDTO toDoctor,
        String referralReason,
        MiniPatientResponseDTO patient,
        String status,
        LocalDate referralDate
) {
}
