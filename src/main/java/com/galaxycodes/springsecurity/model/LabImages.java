package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class LabImages {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "hospitalInImages_id")

    private Hospitals hospitalInImages;

    @ManyToOne
    @JoinColumn(name = "userInImages_id")
    private Users userInImages;

    @ManyToOne
    @JoinColumn(name = "patientInImages_id")
    private Patients patientInImages;

    private LocalDate date=LocalDate.now();

    private String imageTest;
    @Lob
    private byte[] image;

    public LabImages() {
    }

    public LabImages(Hospitals hospitalInImages, Users userInImages, Patients patientInImages, LocalDate date, String imageTest, byte[] image) {
        this.hospitalInImages = hospitalInImages;
        this.userInImages = userInImages;
        this.patientInImages = patientInImages;
        this.date = date;
        this.imageTest = imageTest;
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Hospitals getHospitalInImages() {
        return hospitalInImages;
    }

    public void setHospitalInImages(Hospitals hospitalInImages) {
        this.hospitalInImages = hospitalInImages;
    }

    public Users getUserInImages() {
        return userInImages;
    }

    public void setUserInImages(Users userInImages) {
        this.userInImages = userInImages;
    }

    public Patients getPatientInImages() {
        return patientInImages;
    }

    public void setPatientInImages(Patients patientInImages) {
        this.patientInImages = patientInImages;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getImageTest() {
        return imageTest;
    }

    public void setImageTest(String imageTest) {
        this.imageTest = imageTest;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
