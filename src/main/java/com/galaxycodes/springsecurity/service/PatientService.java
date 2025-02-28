package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.PatientsDTO;
import com.galaxycodes.springsecurity.DTOs.UpdatePatientDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.HospitalsRepo;
import com.galaxycodes.springsecurity.repo.PatientRepo;
import com.galaxycodes.springsecurity.utils.ImagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PatientService {
    @Autowired
    private PatientRepo patientRepo;



    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTService jwtService;



    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<?> createPatient(PatientsDTO dto) throws IOException {

        if(patientRepo.findByUserName(dto.userName()) != null) {
            return new ResponseEntity<>("User already exists", HttpStatus.CONFLICT);
        }


        var patient = toPatient(dto);


        patientRepo.save(patient);

        Map<String, String> response = new HashMap<>();
        response.put("message", "patient created");

        return ResponseEntity.ok(response);
    }

    private Patients toPatient(PatientsDTO dto) throws IOException {
        var patient = new Patients();
        patient.setUserName(dto.userName());
        patient.setFirstName(dto.firstName());
        patient.setLastName(dto.lastName());
        patient.setEmail(dto.email());
        patient.setPassword(encoder.encode(dto.password()));
        patient.setAddress(dto.address());
        patient.setPhone(dto.phoneNumber());


        if (dto.hospitalId() != null) {
            var hospitals = new Hospitals();

            hospitals.setId(dto.hospitalId());
            patient.setHospitals(List.of(hospitals));
        }

        return patient;


    }
    @Transactional
    public byte[] downloadProfile(String username) throws IOException {
        Patients user= patientRepo.findByUserName(username);

        byte[] image = ImagesUtil.decompressImage(user.getProfileImageData());
        return image;
    }
    @Transactional
    public ResponseEntity<?> updateProfile(String username, MultipartFile file) throws IOException {

        Patients user = patientRepo.findByUserName(username);


        user.setProfileImageData(ImagesUtil.compressImage(file.getBytes()));

        patientRepo.save(user);
        Map<String, String> response = new HashMap<>();
        response.put("message", "profile picture updated");

        return ResponseEntity.ok(response);


    }
    public ResponseEntity<?> patientLogin(Patients patient) {
        Patients p = patientRepo.findByUserName(patient.getUserName());
        if (p == null) {return new ResponseEntity<>("patient not found", HttpStatus.NOT_FOUND);}
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(patient.getUserName(),patient.getPassword()));

            if (authentication.isAuthenticated()) {
                Map<String, String> response = new HashMap<>();
                response.put("patient", patient.getUserName());
                response.put("Token", jwtService.generateToken(patient.getUserName()));

                return ResponseEntity.ok(response);
//                return ResponseEntity.ok("Patient "+patient.getUserName()+"Token: "+jwtService.generateToken(patient.getUserName()));

            }
        }catch (AuthenticationException exception){return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or password");}
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or password");

    }

    public ResponseEntity<?> updatePatientsDetails(String username, UpdatePatientDTO dto) {
        var patient = patientRepo.findByUserName(username);
        patient.setFirstName(dto.firstName());
        patient.setLastName(dto.lastName());
        patient.setEmail(dto.email());
        patient.setPassword(encoder.encode(dto.password()));
        patient.setAddress(dto.address());
        patient.setPhone(dto.phoneNumber());
        patient.setUserName(dto.userName());
        patientRepo.save(patient);

        Map<String, String> response = new HashMap<>();
        response.put("message", "patient updated successfully");


        return ResponseEntity.ok(response);



    }

    public ResponseEntity<?> deletePatient(String username) {
        var user = patientRepo.findByUserName(username);
        patientRepo.delete(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "patient deleted");


        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> getAllPatients() {
        return new ResponseEntity<>(patientRepo.findAll(), HttpStatus.OK);
    }
}
