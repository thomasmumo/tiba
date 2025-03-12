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
        double weight,
        double height,
        String bloodPressure,
        String bloodType,
        String age,
        String address,
        Boolean InProgress,
        String phone,
        Boolean sendToDoctor,
        double temperature,
        List<Appointments> appointments,
        List<MedicalRecords> medicalRecords,
        List<Referrals> referrals,
        List<Hospitals> hospitals
) {
}
