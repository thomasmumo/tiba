package com.galaxycodes.springsecurity.controller;


import com.galaxycodes.springsecurity.DTOs.PrescriptionDTO;
import com.galaxycodes.springsecurity.service.MedicalRecordsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@RestController
public class MedicalRecordsController {
    @Autowired
    private MedicalRecordsService medicalRecordsService;

    @PostMapping("/medical-records/{patient-id}/{doc-id}/{hospital-id}/create-medical-record")
    public ResponseEntity<?> createMedicalRecord(@PathVariable("patient-id") Integer patientID,@PathVariable("hospital-id") Integer hospitalID,@PathVariable("doc-id")Integer docID) {
        return medicalRecordsService.createRecord(patientID,hospitalID,docID);
    }
    @PutMapping("/medical-records/{doctor-id}/{patient-id}/assign-doctor")
    public ResponseEntity<?> assignDoctor(@PathVariable("doctor-id") Integer doctorID,@PathVariable("patient-id") Integer patientID) {
        return medicalRecordsService.assignDoctor(doctorID,patientID);
    }

    @PutMapping("/medical-records/{labTech-id}/{patient-id}/{tests}/assign-lab-tech")
    public ResponseEntity<?> assignLabTech(@PathVariable("labTech-id") Integer labTechID,@PathVariable("patient-id") Integer patientID,@PathVariable("tests") String tests) {
        return medicalRecordsService.assignLabTech(labTechID,patientID,tests);
    }

    @PutMapping("/medical-records/{imagingTech-id}/{patient-id}/{tests}/assign-imaging-tech")
    public ResponseEntity<?> assignImagingTech(@PathVariable("imagingTech-id") Integer imagingTechID,@PathVariable("patient-id") Integer patientID,@PathVariable("tests")String tests) {
        return medicalRecordsService.assignImagingTech(imagingTechID,patientID,tests);
    }
    @PutMapping("/medical-records/{labTech-id}/{patient-id}/{tests-results}/put-lab-results")
    public ResponseEntity<?> putLabResults(@PathVariable("labTech-id") Integer labTechID,@PathVariable("patient-id") Integer patientID,@PathVariable("tests-results")String testsResults) {
        return medicalRecordsService.putLabResults(labTechID,patientID,testsResults);
    }
    @PutMapping("medical-records/{patient-id}/{imagingTech-id}/{testName}/put-imaging-results")
    public ResponseEntity<?> putImagingResults(@RequestParam MultipartFile file, @PathVariable("patient-id") Integer patientID, @PathVariable("testName")String testName, @PathVariable("imagingTech-id") Integer imagingTechID) throws IOException {
        return medicalRecordsService.putImagingResults(imagingTechID,patientID,file,testName);
    }
    @GetMapping("/medical-records/{patient-id}/{testName}/get-test-image")
    public ResponseEntity<?> downloadTestImage(@PathVariable("patient-id") Integer patientId,@PathVariable("testName") String testName) throws IOException {
        byte[] imageData = medicalRecordsService.downloadTestImage(patientId,testName);
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

    @GetMapping("/medical-records/{patient-id}/get-all-records")
    public ResponseEntity<?> getAllRecords(@PathVariable("patient-id") Integer patientID) throws IOException {
        return medicalRecordsService.getAllRecords(patientID);
    }
    @GetMapping("/medical-records/{hospital-id}/get-all-records")
    public ResponseEntity<?> getAllHospitalRecords(@PathVariable("hospital-id") Integer hospitalID) throws IOException {
        return medicalRecordsService.getAllHospitalRecords(hospitalID);
    }
    @PutMapping("/medical-records/{patient-id}/prescription")
    public ResponseEntity<?> prescription(@Valid @RequestBody PrescriptionDTO dto,@PathVariable("patient-id") Integer patientID) {
        return medicalRecordsService.prescription(patientID,dto);
    }


}
