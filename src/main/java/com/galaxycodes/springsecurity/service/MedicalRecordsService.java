package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.PrescriptionDTO;
import com.galaxycodes.springsecurity.model.*;
import com.galaxycodes.springsecurity.repo.LabImagesRepo;
import com.galaxycodes.springsecurity.repo.MedicalRecordsRepo;
import com.galaxycodes.springsecurity.utils.ImagesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MedicalRecordsService {
    @Autowired
    private MedicalRecordsRepo medicalRecordsRepo;

    @Autowired
    private LabImagesRepo imagesRepo;





    public ResponseEntity<?> createRecord(Integer patientID,Integer hospitalID, Integer doctorID) {
        var medicalRecord = new MedicalRecords();
        var patient = new Patients();
        patient.setId(patientID);

        var hospital = new Hospitals();
        hospital.setId(hospitalID);

        var doc = new Users();
        doc.setId(doctorID);


        medicalRecord.setPatient(patient);
        medicalRecord.setHospital(hospital);
        medicalRecord.setUser(doc);
        medicalRecordsRepo.save(medicalRecord);

        Map<String, String> response = new HashMap<>();
        response.put("message", "New medical record created successfully");

        return ResponseEntity.ok(response);


    }

    public ResponseEntity<?> assignDoctor(Integer doctorID, Integer patientID) {
        var medicalRecord = medicalRecordsRepo.findAllByPatient_id(patientID)
                .stream()
                .filter(rec -> rec.getMedicalRecordStatus().equals("Open"))
                .collect(Collectors.toList()).get(0);

        var doctor = new Users();
        doctor.setId(doctorID);

        medicalRecord.setUser(doctor);
        medicalRecordsRepo.save(medicalRecord);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Doctor assigned successfully");

        return ResponseEntity.ok(response);



    }

    public ResponseEntity<?> assignLabTech(Integer labTechID, Integer patientID, String tests) {
        List<String> testList = Arrays.stream(tests.split(","))
                .map(String::trim) // Remove whitespace
                .filter(test -> !test.isEmpty()) // Remove empty values
                .collect(Collectors.toList());

        //List<String> testsResults = Collections.nCopies(testList.size(), "null");
        List<String> testsResults=new ArrayList<>(Collections.nCopies(testList.size(), "null"));

        var medicalRecord = medicalRecordsRepo.findAllByPatient_id(patientID)
                .stream()
                .filter(rec -> rec.getMedicalRecordStatus().equals("Open"))
                .collect(Collectors.toList()).get(0);

        medicalRecord.setLabTechId(labTechID);
        medicalRecord.setLabTest(testList);
        medicalRecord.setLabComments(testsResults);
        medicalRecordsRepo.save(medicalRecord);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Lab tech assigned successfully");

        return ResponseEntity.ok(response);

    }
    public ResponseEntity<?> assignImagingTech(Integer imagingTechID, Integer patientID, String imageTests) {
        List<String> imageTestList = Arrays.stream(imageTests.split(","))
                .map(String::trim) // Remove whitespace
                .filter(test -> !test.isEmpty()) // Remove empty values
                .collect(Collectors.toList());
//        List<String> ImagingTestsResults = new ArrayList<>(Collections.nCopies(imageTestList.size(), "null"));
        for(String imageTest : imageTestList) {
            LabImages rec = new LabImages();

            Patients patient = new Patients();
            patient.setId(patientID);
            rec.setPatientInImages(patient);

            Users user = new Users();
            user.setId(imagingTechID);
            rec.setUserInImages(user);

            rec.setImageTest(imageTest);
            imagesRepo.save(rec);

        }
        var medicalRecord = medicalRecordsRepo.findAllByPatient_id(patientID)
                .stream()
                .filter(rec -> rec.getMedicalRecordStatus().equals("Open"))
                .collect(Collectors.toList()).get(0);

        medicalRecord.setImagingTechId(imagingTechID);
        medicalRecord.setImagingTests(imageTestList);

        medicalRecordsRepo.save(medicalRecord);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Imaging tech assigned successfully");

        return ResponseEntity.ok(response);

    }




    public ResponseEntity<?> putLabResults(Integer labTechID, Integer patientID,String testsResults) {
        List<String> testResultsList = Arrays.stream(testsResults.split(","))
                .map(String::trim) // Remove whitespace
                .filter(test -> !test.isEmpty()) // Remove empty values
                .collect(Collectors.toList());
        var record = medicalRecordsRepo.findAllByPatient_id(patientID)
                .stream()
                .filter(rec -> rec.getMedicalRecordStatus().equals("Open") && rec.getLabTechId().equals(labTechID))
                .collect(Collectors.toList()).get(0);

        if(record == null){
            Map<String, String> response = new HashMap<>();
            response.put("message", "Medical record already closed");

            return ResponseEntity.ok(response);

        }


        int idCounter = 0;
        for(String testResult : testResultsList){
            if (!Objects.equals(testResult, "null") && idCounter <= testResultsList.size() - 1){
                record.getLabComments().set(idCounter, testResult);
                idCounter++;
            }

        }
        record.setLabComments(testResultsList);
        medicalRecordsRepo.save(record);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Lab results saved successfully");

        return ResponseEntity.ok(response);



    }
    @Transactional
    public ResponseEntity<?> putImagingResults( Integer imagingTechID,Integer patientID, MultipartFile file,String testName) throws IOException {
        var record = imagesRepo.findAllByPatientInImages_id(patientID)
                .stream()
                .filter(rec -> rec.getDate().equals(LocalDate.now()) && rec.getImageTest().equals(testName))
                .collect(Collectors.toList()).get(0);



        record.setImage(ImagesUtil.compressImage(file.getBytes()));
        imagesRepo.save(record);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Imaging results saved successfully");

        return ResponseEntity.ok(response);




    }
    @Transactional
    public byte[] downloadTestImage(Integer patientID,String testName) throws IOException {
        var record = imagesRepo.findAllByPatientInImages_id(patientID)
                .stream()
                .filter(rec -> rec.getDate().equals(LocalDate.now()) && rec.getImageTest().equals(testName))
                .collect(Collectors.toList()).get(0);


        if(record != null){
            byte[] image = ImagesUtil.decompressImage(record.getImage());
            return image;
        }
        return null;

    }

    public ResponseEntity<?> getAllRecords(Integer patientID) {
        var records = medicalRecordsRepo.findAllByPatient_id(patientID);
        return new ResponseEntity<>(records, HttpStatus.OK);
    }

    public ResponseEntity<?> prescription(Integer patientID, PrescriptionDTO dto) {
        var record = medicalRecordsRepo.findAllByPatient_id(patientID)
                .stream()
                .filter(rec -> rec.getMedicalRecordStatus().equals("Open"))
                .collect(Collectors.toList()).get(0);
        record.setPrescription(dto.prescription());
        record.setCondition(dto.condition());
        record.setMedicalRecordStatus("Closed");
        medicalRecordsRepo.save(record);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Prescription saved successfully");

        return ResponseEntity.ok(response);

    }
}
