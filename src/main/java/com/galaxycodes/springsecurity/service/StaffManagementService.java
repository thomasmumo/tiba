package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.StaffManagementDTO;
import com.galaxycodes.springsecurity.repo.StaffManagementRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class StaffManagementService {
    @Autowired
    private StaffManagementRepo staffRepo;

    public ResponseEntity<?> getHospitalRecords(Integer hospitalId) {
        return new ResponseEntity<>(staffRepo.findAllByHospital_id(hospitalId), HttpStatus.OK);
    }
}
