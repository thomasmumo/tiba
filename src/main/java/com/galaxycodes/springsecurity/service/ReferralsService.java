package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.*;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Referrals;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.HospitalsRepo;
import com.galaxycodes.springsecurity.repo.ReferralsRepo;
import com.galaxycodes.springsecurity.repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ReferralsService {
    @Autowired
    private ReferralsRepo referralsRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private HospitalsRepo hospitalsRepo;
    public ResponseEntity<?> createReferral(Integer doctorID, @Valid ReferralsDTO dto) {
        var referral = toReferral(doctorID,dto);
        var patientReferrals = referralsRepo.findAllByPatient_id(dto.patientID())
                .stream()
                .filter(ref ->ref.getTo_hospital_id().equals(dto.toHospitalID()))
                .collect(Collectors.toList());
        if(!patientReferrals.isEmpty()){
            Map<String, String> response = new HashMap<>();
            response.put("message", "Patient already referred to "+hospitalsRepo.findById(dto.toHospitalID()).get().getHospitalName());

            return ResponseEntity.ok(response);

        }
        referralsRepo.save(referral);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Patient referred to "+hospitalsRepo.findById(referral.getTo_hospital_id()).get().getHospitalName());

        return ResponseEntity.ok(response);


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

    public List<ReferralsResponseDTO> receivedReferrals(Integer doctorID) {
        var referrals = referralsRepo.findAllByToStaffId(doctorID);
        return referrals.stream()
                .map(referral -> new ReferralsResponseDTO(
                        referral.getId(),
                        new HospitalDTO(referral.getHospital().getHospitalName(),referral.getHospital().getLocation()),
                        referral.getTo_hospital_id(),
                        new UsersResponseDTO(referral.getUserInReferrals().getId(),referral.getUserInReferrals().getFirstName(),referral.getUserInReferrals().getLastName()),
                        referral.getToStaffId(),
                        referral.getReferralReason(),
                        referral.getPatient(),
                        referral.getReferralStatus(),
                        referral.getReferralDate()
                ))
                .toList();
    }

    public List<ReferralsResponseDTO> sendReferrals(Integer doctorID) {
        var referrals = referralsRepo.findAllByUserInReferrals_id(doctorID);
        return referrals.stream()
                .map(referral -> new ReferralsResponseDTO(
                        referral.getId(),
                        new HospitalDTO(referral.getHospital().getHospitalName(),referral.getHospital().getLocation()),
                        referral.getTo_hospital_id(),
                        new UsersResponseDTO(referral.getUserInReferrals().getId(),referral.getUserInReferrals().getFirstName(),referral.getUserInReferrals().getLastName()),
                        referral.getToStaffId(),
                        referral.getReferralReason(),
                        referral.getPatient(),
                        referral.getReferralStatus(),
                        referral.getReferralDate()
                ))
                .toList();
    }

    public List<UpdatedResponseDTO> patientReferrals(Integer patientID) {
        var referrals = referralsRepo.findAllByPatient_id(patientID);
        return referrals.stream()
                .map(referral -> new UpdatedResponseDTO(
                        referral.getId(),
                        new HospitalResponseDTO(referral.getHospital().getId(),referral.getHospital().getHospitalName(),referral.getHospital().getLocation(),referral.getHospital().getUsers()),
                        new HospitalResponseDTO(hospitalsRepo.findById(referral.getTo_hospital_id()).get().getId(),hospitalsRepo.findById(referral.getTo_hospital_id()).get().getHospitalName(),hospitalsRepo.findById(referral.getTo_hospital_id()).get().getLocation(),hospitalsRepo.findById(referral.getTo_hospital_id()).get().getUsers()),
                        new DoctorResponseDTO(referral.getUserInReferrals().getId(),referral.getUserInReferrals().getFirstName(),referral.getUserInReferrals().getLastName(),referral.getUserInReferrals().getUserName()),
                        new DoctorResponseDTO(userRepo.findById(referral.getToStaffId()).get().getId(),userRepo.findById(referral.getToStaffId()).get().getFirstName(),userRepo.findById(referral.getToStaffId()).get().getLastName(),userRepo.findById(referral.getToStaffId()).get().getUserName()),
                        referral.getReferralReason(),
                        new MiniPatientResponseDTO(referral.getPatient().getId(),referral.getPatient().getFirstName(),referral.getPatient().getLastName(),referral.getPatient().getUserName()),
                        referral.getReferralStatus(),
                        referral.getReferralDate()
                ))
                .toList();
    }

    public ResponseEntity<?> update(Integer referralID) {
        var ref = referralsRepo.findById(referralID).get();
        ref.setReferralStatus("Seen");
        referralsRepo.save(ref);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Successfully updated referral");

        return ResponseEntity.ok(response);
    }
}
