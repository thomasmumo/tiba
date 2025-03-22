package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Patients {
    @Id
    @GeneratedValue
    private Integer id;
    @Column(unique=true)
    private String userName;
    private String password;
    @Column(unique=true)
    private String email;
    private String sex;
    private String allergies;

    private double weight;
    private double height;
    private double temperature;
    private String bloodPressure;
    private  String bloodType;
    private String age;
    private String profileURL;
    private String address;
    private Boolean inProgress = false;
    private String firstName;
    private String lastName;
    private String phone;
    private boolean sendToDoctor = false;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Appointments> appointments;
    @OneToMany(mappedBy = "patientInImages")
    @JsonIgnore
    private List<LabImages> images;

    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<MedicalRecords> medicalRecords;
    @OneToMany(mappedBy = "patient")
    @JsonIgnore
    private List<Referrals> referrals;
    @ManyToMany
    @JoinTable(
            name = "patients_hospitals",
            joinColumns={
                    @JoinColumn(name = "patient_id")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "hospital_id")
            }
    )
    @JsonIgnore
    private List<Hospitals> hospitals =new ArrayList<>();

    public Patients() {
    }



    public Patients(List<LabImages> images, boolean sendToDoctor,double temperature,String profileURL, String phone, Boolean inProgress, List<Referrals> referrals, String userName, String password, String email, String sex, String allergies, double weight, double height, String bloodPressure, String bloodType, String age, String address, String firstName, String lastName, List<Appointments> appointments, List<MedicalRecords> medicalRecords, List<Hospitals> hospitals) {
        this.userName = userName;
        this.password = password;
        this.sendToDoctor = sendToDoctor;
        this.inProgress = inProgress;
        this.temperature = temperature;
        this.referrals = referrals;
        this.images = images;
        this.phone = phone;
        this.profileURL = profileURL;
        this.email = email;
        this.sex = sex;
        this.allergies = allergies;
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.bloodType = bloodType;
        this.age = age;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointments = appointments;
        this.medicalRecords = medicalRecords;
        this.hospitals = hospitals;
    }

    public boolean isSendToDoctor() {
        return sendToDoctor;
    }

    public void setSendToDoctor(boolean sendToDoctor) {
        this.sendToDoctor = sendToDoctor;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Boolean getInProgress() {
        return inProgress;
    }

    public void setInProgress(Boolean inProgress) {
        this.inProgress = inProgress;
    }

    public List<LabImages> getImages() {
        return images;
    }

    public void setImages(List<LabImages> images) {
        this.images = images;
    }

    public List<Referrals> getReferrals() {
        return referrals;
    }

    public void setReferrals(List<Referrals> referrals) {
        this.referrals = referrals;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(String bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public List<Appointments> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointments> appointments) {
        this.appointments = appointments;
    }

    public List<MedicalRecords> getMedicalRecords() {
        return medicalRecords;
    }

    public void setMedicalRecords(List<MedicalRecords> medicalRecords) {
        this.medicalRecords = medicalRecords;
    }

    public List<Hospitals> getHospitals() {
        return hospitals;
    }

    public void setHospitals(List<Hospitals> hospitals) {
        this.hospitals = hospitals;
    }

    public String getProfileURL() {
        return profileURL;
    }

    public void setProfileURL(String profileURL) {
        this.profileURL = profileURL;
    }
}
