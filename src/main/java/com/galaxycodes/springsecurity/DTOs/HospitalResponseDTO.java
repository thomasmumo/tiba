package com.galaxycodes.springsecurity.DTOs;

import com.galaxycodes.springsecurity.model.Users;

import java.util.List;

public record HospitalResponseDTO(
        Integer id, String name, String location, List<Users> users) {
}
