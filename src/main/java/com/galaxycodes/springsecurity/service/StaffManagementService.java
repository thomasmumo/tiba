package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.StaffManagementDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StaffManagementService {
    public ResponseEntity<?> createRecord(@Valid StaffManagementDTO dto) {
        return null;
    }
}
