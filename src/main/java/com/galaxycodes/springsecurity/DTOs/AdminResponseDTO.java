package com.galaxycodes.springsecurity.DTOs;

public record AdminResponseDTO(
        String firstname,
        String lastname,
        Integer phone,
        Boolean activeAcc,
        Boolean loggedIn,
        Integer userId,
        Integer hospitalID,
        String hospitalName,
        String hospitalLocation,
        String username,
        String profileURL,
        String role,
        boolean acceptReferrals
) {

}
