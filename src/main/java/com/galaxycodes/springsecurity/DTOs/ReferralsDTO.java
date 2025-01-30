package com.galaxycodes.springsecurity.DTOs;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record ReferralsDTO(
        @NotNull(message = "Referral reason is required") @Size(min = 1, message = " Referral reason is required")
        String referralReason,
        @NotNull(message = "Doctor ID for the target hospital is required")
        Integer toStaffId,
        @NotNull(message = "Hospital ID for the current hospital is required")
        Integer fromHospitalID,
        @NotNull(message = "Patient ID is required")
        Integer patientID,
        @NotNull(message = "Hospital ID for the target hospital is required")
        Integer toHospitalID)
{
}
