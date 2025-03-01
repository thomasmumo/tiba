package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.StaffManagementDTO;
import com.galaxycodes.springsecurity.service.StaffManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@CrossOrigin(origins = "*")
@RestController
public class StaffManagementController {

    @Autowired
    private StaffManagementService staffManagementService;



    @GetMapping("/staff-management/hospital-records/{hospital-id}")
    public ResponseEntity<?> getRecords(@PathVariable("hospital-id") Integer hospitalId){
        return staffManagementService.getHospitalRecords(hospitalId);
    }
}
