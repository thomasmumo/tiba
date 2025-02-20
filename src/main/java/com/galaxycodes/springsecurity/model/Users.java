package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Users {

    @Id
    @GeneratedValue
    private Integer id;
    private String firstName;
    private String lastName;

    @Column(unique=true)
    private String userName;
    private String password;
    private boolean isActive=true;
    private String role;
    private Integer phoneNumber;
    private String profileURL;
    private String email;
    private boolean loggedIn;



    @OneToMany(mappedBy = "user")

    private List<MedicalRecords> medicalRecords;
    @OneToMany(mappedBy = "userInImages")

    private List<LabImages> images;

    @OneToMany(mappedBy = "userInReferrals")

    private List<Referrals> referrals;

    @OneToMany(mappedBy = "userInStaffManagement")

    private List<StaffManagement> attendance;

    @OneToMany(mappedBy = "userInAppointment")

    private List<Appointments> appointments;

    @ManyToOne
    @JoinColumn(name = "hospitalInUsers_id")

    private Hospitals hospitalInUsers;

    public Users() {
    }



    public Users(List<LabImages> images,boolean loggedIn, String email, Integer phoneNumber, String firstName, String lastName, String profileURL, String userName, String password, boolean isActive, String role, List<MedicalRecords> medicalRecords, List<Referrals> referrals, List<StaffManagement> attendance, List<Appointments> appointments, Hospitals hospitalInUsers) {
        this.userName = userName;
        this.password = password;
        this.loggedIn = loggedIn;
        this.images = images;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.isActive = isActive;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.medicalRecords = medicalRecords;
        this.referrals = referrals;
        this.attendance = attendance;
        this.appointments = appointments;
        this.hospitalInUsers = hospitalInUsers;
        this.profileURL = profileURL;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public List<LabImages> getImages() {
        return images;
    }

    public void setImages(List<LabImages> images) {
        this.images = images;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Integer phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
        this.medicalRecords = medicalRecords;
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

    public List<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointments> appointments) {
        this.appointments = appointments;
    }

    public Hospitals getHospitalInUsers() {
        return hospitalInUsers;
    }

    public void setHospitalInUsers(Hospitals hospitalInUsers) {
        this.hospitalInUsers = hospitalInUsers;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}
