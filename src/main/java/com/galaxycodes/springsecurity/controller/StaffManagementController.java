package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.StaffManagementDTO;
import com.galaxycodes.springsecurity.DTOs.StaffManagementResponseDTO;
import com.galaxycodes.springsecurity.service.StaffManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class StaffManagementController {

    @Autowired
    private StaffManagementService staffManagementService;



    @GetMapping("/staff-management/hospital-records/{hospital-id}")
    public List<StaffManagementResponseDTO> getRecords(@PathVariable("hospital-id") Integer hospitalId){
        return staffManagementService.getHospitalRecords(hospitalId);
    }
}
