package com.galaxycodes.springsecurity.DTOs;

public record PatientRecordDTO(
        Integer id,
        String firstName,
        String lastName,
        String userName,


        double weight,

        String bloodPressure,

        String age,


        double temperature
) {
}
