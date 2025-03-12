package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class MedicalRecords {
    ZoneId nairobiZone = ZoneId.of("Africa/Nairobi");
    @Id
    @GeneratedValue
    private Integer id;
    private String condition;
    private String prescription;
    private String symptoms;
    private String docComments;
    @ElementCollection
    private List<String> labTest;
    @ElementCollection
    private List<String> labComments;
    private Integer labTechId;
    @ElementCollection
    private List<String> imagingTests;
    @ElementCollection

    private List<String> imagingPath;
    private Integer imagingTechId;
    private LocalDate date = LocalDate.now(nairobiZone);
    private String medicalRecordStatus = "Open";
    @ManyToOne
    @JoinColumn(name = "user_id")

    private Users user;

    @ManyToOne
    @JoinColumn(name = "hospital_id")

    private Hospitals hospital;

    @ManyToOne
    @JoinColumn(name = "patient_id")

    private Patients patient;


    public MedicalRecords() {
    }



    public MedicalRecords(List<String> labComments,String symptoms, String medicalRecordStatus, Integer labTechId, List<String> imagingPath, Integer imagingTechId, String condition, String prescription, String docComments, List<String> labTest, List<String> imagingTests, LocalDate date, Users user, Hospitals hospital, Patients patient) {
        this.condition = condition;
        this.labComments = labComments;
        this.medicalRecordStatus = medicalRecordStatus;
        this.labTechId = labTechId;
        this.symptoms = symptoms;
        this.imagingPath = imagingPath;
        this.imagingTechId = imagingTechId;
        this.prescription = prescription;
        this.docComments = docComments;
        this.labTest = labTest;
        this.imagingTests = imagingTests;
        this.date = date;
        this.user = user;
        this.hospital = hospital;
        this.patient = patient;
    }

    public String getSymptoms() {
        return symptoms;
    }

    public void setSymptoms(String symptoms) {
        this.symptoms = symptoms;
    }

    public String getMedicalRecordStatus() {
        return medicalRecordStatus;
    }

    public void setMedicalRecordStatus(String medicalRecordStatus) {
        this.medicalRecordStatus = medicalRecordStatus;
    }



    public Integer getLabTechId() {
        return labTechId;
    }

    public void setLabTechId(Integer labTechId) {
        this.labTechId = labTechId;
    }


    public List<String> getLabComments() {
        return labComments;
    }

    public void setLabComments(List<String> labComments) {
        this.labComments = labComments;
    }

    public List<String> getImagingPath() {
        return imagingPath;
    }

    public void setImagingPath(List<String> imagingPath) {
        this.imagingPath = imagingPath;
    }

    public Integer getImagingTechId() {
        return imagingTechId;
    }

    public void setImagingTechId(Integer imagingTechId) {
        this.imagingTechId = imagingTechId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getDocComments() {
        return docComments;
    }

    public void setDocComments(String docComments) {
        this.docComments = docComments;
    }

    public List<String> getLabTest() {
        return labTest;
    }

    public void setLabTest(List<String> labTest) {
        this.labTest = labTest;
    }

    public List<String> getImagingTests() {
        return imagingTests;
    }

    public void setImagingTests(List<String> imagingTests) {
        this.imagingTests = imagingTests;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Hospitals getHospital() {
        return hospital;
    }

    public void setHospital(Hospitals hospital) {
        this.hospital = hospital;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }
}
