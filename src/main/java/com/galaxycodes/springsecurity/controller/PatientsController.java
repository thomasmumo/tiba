package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.ChangePasswordDTO;
import com.galaxycodes.springsecurity.DTOs.PatientResponseDTO;
import com.galaxycodes.springsecurity.DTOs.PatientsDTO;
import com.galaxycodes.springsecurity.DTOs.UpdatePatientDTO;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PatientsController {
    @Autowired
    private PatientService patientService;


    @PostMapping("/patients/create-patient")
    public ResponseEntity<?> createPatient(@Valid @RequestBody PatientsDTO dto) throws IOException {
        return patientService.createPatient(dto);
    }

    @GetMapping("/patients/all-patients")
    public ResponseEntity<?> getAllPatients() {
        return patientService.getAllPatients();
    }
    @GetMapping("/patients/{username}/get-patient")
    public PatientResponseDTO getPatient(@PathVariable("username") String userName) {
        return patientService.getPatient(userName);
    }


    @PostMapping("/patients/login")
    public ResponseEntity<?> login(@RequestBody Patients patient) {
        return patientService.patientLogin(patient);
    }
    @PutMapping("/patients/{user-name}/update-patient-details")
    public ResponseEntity<?> updatePatientDetails(@PathVariable("user-name") String username, @RequestBody UpdatePatientDTO dto) {
        return patientService.updatePatientsDetails(username,dto);
    }
    @PutMapping("/patients/{userName}/add-hospital/{hospitalId}")
    public ResponseEntity<?> addHospital(@PathVariable("userName") String username, @PathVariable("hospitalId") Integer hospitalID) {
        return patientService.addHospital(username,hospitalID);
    }
    @PutMapping("/patients/{userName}/start-process")
    public ResponseEntity<?> startProcess(@PathVariable("userName") String username) {
        return patientService.startProcess(username);
    }
    @PutMapping("/patients/{userName}/change-password")
    public ResponseEntity<?> changePassword(@PathVariable("userName") String username,@RequestBody ChangePasswordDTO dto) {
        return patientService.changePatientPassword(username,dto);
    }

    @PutMapping("/patients/{username}/end-process")
    public ResponseEntity<?> endProcess(@PathVariable("username") String username) {
        return patientService.endProcess(username);
    }

    @DeleteMapping("/patients/{user-name}/delete-patient")
    public ResponseEntity<?> deletePatient(@PathVariable("user-name") String username) {
        return patientService.deletePatient(username);
    }

    @PutMapping("/patients/{userName}/update-profile-pic")
    public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile file,@PathVariable("userName") String username) throws IOException {
        return patientService.updateProfile(username,file);
    }


    @GetMapping("/patients/{userName}/get-profile-pic")
    public ResponseEntity<?> downloadProfile(@PathVariable(value = "userName") String userName) throws IOException {
        return this.patientService.downloadProfile(userName);
    }



    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        var errors =  new HashMap<String, String>();

        exp.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
