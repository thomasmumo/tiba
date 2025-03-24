package com.galaxycodes.springsecurity.DTOs;

public record UsersManagementResponseDTO(
        String firstName,

        String lastName,

        String userName,
        String role,
        boolean acceptReferrals,

        Integer phoneNumber,


        String email,
        boolean loggedIn,
        String profileURL
) {
}
