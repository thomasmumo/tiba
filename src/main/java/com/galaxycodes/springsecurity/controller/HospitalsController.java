package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.HospitalDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.service.HospitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HospitalsController {
    @Autowired
    private HospitalsService hospitalsService;
    @PostMapping("/hospitals/create-hospital")
    public ResponseEntity<?> createHospital(@RequestBody HospitalDTO dto) {
        return hospitalsService.createHospital(dto);
    }

    @GetMapping("/hospitals/all-hospitals")
    public List<HospitalDTO> getAllHospitals() {

        return hospitalsService.getAllHospitals();
    }
}
