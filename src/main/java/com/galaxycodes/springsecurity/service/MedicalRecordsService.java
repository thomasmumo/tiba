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

    private final String FOLDER_PATH = "C:\\Users\\User\\Desktop\\LAB\\";



    public ResponseEntity<?> createRecord(Integer patientID,Integer hospitalID) {
        var medicalRecord = new MedicalRecords();
        var patient = new Patients();
        patient.setId(patientID);

        var hospital = new Hospitals();
        hospital.setId(hospitalID);


        medicalRecord.setPatient(patient);
        medicalRecord.setHospital(hospital);
        medicalRecordsRepo.save(medicalRecord);

        return new ResponseEntity<>("New medical record created successfully", HttpStatus.CREATED);
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

        return new ResponseEntity<>("Doctor assigned successfully", HttpStatus.CREATED);

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
        return new ResponseEntity<>("Lab tech assigned successfully", HttpStatus.CREATED);
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
        return new ResponseEntity<>("Imaging tech assigned successfully", HttpStatus.CREATED);
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

        if(record == null){return new ResponseEntity<>("Medical record already closed",HttpStatus.OK);}


        int idCounter = 0;
        for(String testResult : testResultsList){
            if (!Objects.equals(testResult, "null") && idCounter <= testResultsList.size() - 1){
                record.getLabComments().set(idCounter, testResult);
                idCounter++;
            }

        }
        record.setLabComments(testResultsList);
        medicalRecordsRepo.save(record);
        return new ResponseEntity<>("Lab results saved successfully", HttpStatus.CREATED);


    }
    @Transactional
    public ResponseEntity<?> putImagingResults( Integer imagingTechID,Integer patientID, MultipartFile file,String testName) throws IOException {
        var record = imagesRepo.findAllByPatientInImages_id(patientID)
                .stream()
                .filter(rec -> rec.getDate().equals(LocalDate.now()) && rec.getImageTest().equals(testName))
                .collect(Collectors.toList()).get(0);



        record.setImage(ImagesUtil.compressImage(file.getBytes()));
        imagesRepo.save(record);
        return new ResponseEntity<>("Imaging results saved successfully", HttpStatus.CREATED);



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
        return new ResponseEntity<>("Prescription saved successfully", HttpStatus.CREATED);
    }
}
