package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.ReferralsDTO;
import com.galaxycodes.springsecurity.service.ReferralsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<?> receivedReferrals(@PathVariable("doctor-id") Integer doctorID){
        return referralsService.receivedReferrals(doctorID);
    }
    @GetMapping("/referrals/{doctor-id}/get-send-referrals")
    public ResponseEntity<?> sendReferrals(@PathVariable("doctor-id") Integer doctorID){
        return referralsService.sendReferrals(doctorID);
    }
}
