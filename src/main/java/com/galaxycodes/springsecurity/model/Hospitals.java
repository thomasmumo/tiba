package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Hospitals {
    @Id
    @GeneratedValue
    private Integer id;
    private String hospitalName;
    private String location;
    @OneToMany(mappedBy = "hospital")

    private List<MedicalRecords> medicalRecords;

    @OneToMany(mappedBy = "hospitalInUsers")

    private List<Users> users;

    @OneToMany(mappedBy = "hospitalInImages")

    private List<LabImages> images;

    @OneToMany(mappedBy = "hospital")

    private List<Referrals> referrals;

    @OneToMany(mappedBy = "hospital")

    private List<StaffManagement> attendance;

    @OneToMany(mappedBy = "hospital")

    private List<Appointments> appointments;
    @ManyToMany(mappedBy = "hospitals")
    @JsonIgnore
    private List<Patients> patients;

    public Hospitals() {
    }



    public Hospitals(List<LabImages> images,String location, String hospitalName, List<MedicalRecords> medicalRecords, List<Users> users, List<Referrals> referrals, List<StaffManagement> attendance, List<Appointments> appointments, List<Patients> patients) {
        this.hospitalName = hospitalName;
        this.location = location;
        this.medicalRecords = medicalRecords;
        this.images = images;
        this.users = users;
        this.referrals = referrals;
        this.attendance = attendance;
        this.appointments = appointments;
        this.patients = patients;
    }

    public List<LabImages> getImages() {
        return images;
    }

    public void setImages(List<LabImages> images) {
        this.images = images;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public List<Referrals> getReferrals() {
        return referrals;
    }

    public void setReferrals(List<Referrals> referrals) {
        this.referrals = referrals;
    }

    public List<StaffManagement> getAttendance() {
        return attendance;
    }

    public void setAttendance(List<StaffManagement> attendance) {
        this.attendance = attendance;
    }

    public List<Patients> getPatients() {
        return patients;
    }

    public void setPatients(List<Patients> patients) {
        this.patients = patients;
    }

    public List<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointments> appointments) {
        this.appointments = appointments;
    }
}
