package com.galaxycodes.springsecurity.DTOs;

public record AdminResponseDTO(
        String firstname,
        String lastname,
        Integer phone,
        Boolean activeAcc,
        Boolean loggedIn,
        String hospitalName,
        String hospitalLocation,
        String username,
        String profileURL
) {

}
