package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.StaffManagementDTO;
import com.galaxycodes.springsecurity.service.StaffManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StaffManagementController {

    @Autowired
    private StaffManagementService staffManagementService;

    @PostMapping("/staff-management/create-record")
    public ResponseEntity<?> createRecord(@Valid @RequestBody StaffManagementDTO dto){
        return staffManagementService.createRecord(dto);
    }
}
