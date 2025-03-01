package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.HospitalDTO;
import com.galaxycodes.springsecurity.DTOs.HospitalResponseDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.service.HospitalsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class HospitalsController {
    @Autowired
    private HospitalsService hospitalsService;
    @PostMapping("/hospitals/create-hospital")
    public ResponseEntity<?> createHospital(@RequestBody HospitalDTO dto) {
        return hospitalsService.createHospital(dto);
    }

    @GetMapping("/hospitals/all-hospitals")
    public List<HospitalResponseDTO> getAllHospitals() {

        return hospitalsService.getAllHospitals();
    }
    @GetMapping("/hospitals/{hospitalID}/get-hospital")
    public ResponseEntity<?> getHospital(@PathVariable("hospitalID") Integer hospitalID) {

        return hospitalsService.getHospital(hospitalID);
    }
}
