package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.ReferralsDTO;
import com.galaxycodes.springsecurity.DTOs.ReferralsResponseDTO;
import com.galaxycodes.springsecurity.service.ReferralsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class ReferralsController {
    @Autowired
    private ReferralsService referralsService;
    @PostMapping("/referrals/{doctor-id}/create-referral")
    public ResponseEntity<?> createReferral(@PathVariable("doctor-id") Integer doctorID, @Valid @RequestBody ReferralsDTO dto){
        return referralsService.createReferral(doctorID,dto);
    }
    @GetMapping("/referrals/{doctor-id}/get-received-referrals")
    public List<ReferralsResponseDTO> receivedReferrals(@PathVariable("doctor-id") Integer doctorID){
        return referralsService.receivedReferrals(doctorID);
    }
    @GetMapping("/referrals/{doctor-id}/get-send-referrals")
    public List<ReferralsResponseDTO> sendReferrals(@PathVariable("doctor-id") Integer doctorID){
        return referralsService.sendReferrals(doctorID);
    }
    @PutMapping("/referrals/{referral-id}/update-referral")
    public ResponseEntity<?> update(@PathVariable("referral-id") Integer referralID){
        return referralsService.update(referralID);
    }
    @GetMapping("/referrals/{patient-id}/get-patient-referrals")
    public List<ReferralsResponseDTO> patientReferrals(@PathVariable("patient-id") Integer patientID){
        return referralsService.patientReferrals(patientID);
    }
}
