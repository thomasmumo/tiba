package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.ReferralsDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Referrals;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.HospitalsRepo;
import com.galaxycodes.springsecurity.repo.ReferralsRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class ReferralsService {
    @Autowired
    private ReferralsRepo referralsRepo;
    @Autowired
    private HospitalsRepo hospitalsRepo;
    public ResponseEntity<?> createReferral(Integer doctorID, @Valid ReferralsDTO dto) {
        var referral = toReferral(doctorID,dto);
        var patientReferrals = referralsRepo.findAllByPatient_id(dto.patientID())
                .stream()
                .filter(ref ->ref.getTo_hospital_id().equals(dto.toHospitalID()))
                .collect(Collectors.toList());
        if(!patientReferrals.isEmpty()){
            return new ResponseEntity<>("Patient already referred to "+hospitalsRepo.findById(dto.toHospitalID()).get().getHospitalName(),HttpStatus.CONFLICT);
        }
        referralsRepo.save(referral);
        return new ResponseEntity<>("patient referred to "+hospitalsRepo.findById(referral.getTo_hospital_id()).get().getHospitalName()+" successfully.", HttpStatus.CREATED);

    }

    private Referrals toReferral(Integer doctorID, @Valid ReferralsDTO dto) {
        var referral = new Referrals();
        referral.setReferralReason(dto.referralReason());
        referral.setTo_staff_id(dto.toStaffId());
        referral.setTo_hospital_id(dto.toHospitalID());

        var hospital = new Hospitals();
        hospital.setId(dto.fromHospitalID());
        referral.setHospital(hospital);

        var doctor = new Users();
        doctor.setId(doctorID);
        referral.setUserInReferrals(doctor);

        var patient = new Patients();
        patient.setId(dto.patientID());
        referral.setPatient(patient);
        return referral;


    }

    public ResponseEntity<?> receivedReferrals(Integer doctorID) {
        var referrals = referralsRepo.findAllByToStaffId(doctorID);
        if (referrals.isEmpty()) {
            return new ResponseEntity<>("No referrals yet", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(referrals, HttpStatus.FOUND);
    }

    public ResponseEntity<?> sendReferrals(Integer doctorID) {
        var referrals = referralsRepo.findAllByUserInReferrals_id(doctorID);
        if (referrals.isEmpty()) {
            return new ResponseEntity<>("No referrals yet", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(referrals, HttpStatus.FOUND);

    }
}
