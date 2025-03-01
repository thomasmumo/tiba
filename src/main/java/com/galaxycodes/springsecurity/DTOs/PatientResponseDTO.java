package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Appointments;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.MedicalRecords;
import com.galaxycodes.springsecurity.model.Referrals;

import java.util.Date;
import java.util.List;

public record PatientResponseDTO(
        Integer id,
        String fullName,
        String username,
        String email,
        String sex,
        String allergies,
        Integer weight,
        Integer height,
        Integer bloodPressure,
        String bloodType,
        Date birthDate,
        String address,
        Boolean InProgress,
        String phone,
        List<Appointments> appointments,
        List<MedicalRecords> medicalRecords,
        List<Referrals> referrals,
        List<Hospitals> hospitals
) {
}
