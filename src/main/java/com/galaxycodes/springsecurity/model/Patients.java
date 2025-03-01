package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

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

    private Integer weight;
    private Integer height;
    private Integer bloodPressure;
    private  String bloodType;
    private Date birthDate;
    @Lob
    private byte[] profileImageData;
    private String address;
    private Boolean inProgress = false;
    private String firstName;
    private String lastName;
    private String phone;
    @OneToMany(mappedBy = "patient")

    private List<Appointments> appointments;
    @OneToMany(mappedBy = "patientInImages")

    private List<LabImages> images;

    @OneToMany(mappedBy = "patient")

    private List<MedicalRecords> medicalRecords;
    @OneToMany(mappedBy = "patient")
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
    private List<Hospitals> hospitals;

    public Patients() {
    }



    public Patients(List<LabImages> images, String phone, Boolean inProgress,List<Referrals> referrals, byte[] profileImageData, String userName, String password, String email, String sex, String allergies, Integer weight, Integer height, Integer bloodPressure, String bloodType, Date birthDate, String address, String firstName, String lastName, List<Appointments> appointments, List<MedicalRecords> medicalRecords, List<Hospitals> hospitals) {
        this.userName = userName;
        this.password = password;
        this.inProgress = inProgress;
        this.referrals = referrals;
        this.images = images;
        this.phone = phone;
        this.profileImageData = profileImageData;
        this.email = email;
        this.sex = sex;
        this.allergies = allergies;
        this.weight = weight;
        this.height = height;
        this.bloodPressure = bloodPressure;
        this.bloodType = bloodType;
        this.birthDate = birthDate;
        this.address = address;
        this.firstName = firstName;
        this.lastName = lastName;
        this.appointments = appointments;
        this.medicalRecords = medicalRecords;
        this.hospitals = hospitals;
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

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getBloodPressure() {
        return bloodPressure;
    }

    public void setBloodPressure(Integer bloodPressure) {
        this.bloodPressure = bloodPressure;
    }

    public String getBloodType() {
        return bloodType;
    }

    public void setBloodType(String bloodType) {
        this.bloodType = bloodType;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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

    public byte[] getProfileImageData() {
        return profileImageData;
    }

    public void setProfileImageData(byte[] profileImageData) {
        this.profileImageData = profileImageData;
    }
}
