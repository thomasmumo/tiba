package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class StaffManagement {
    @Id
    @GeneratedValue
    private Integer id;
    private LocalDate date=LocalDate.now();
    private LocalTime loggedInTime;
    private LocalTime loggedOutTime;
    private Float workHours;
    private String shift;
    @ManyToOne
    @JoinColumn(name = "userInStaffManagement_id")

    private Users userInStaffManagement;

    @ManyToOne
    @JoinColumn(name = "hospital_id")

    private Hospitals hospital;

    public StaffManagement() {
    }


    public StaffManagement(LocalTime loggedInTime,String shift, LocalTime loggedOutTime, LocalDate date, Float workHours, Users userInStaffManagement, Hospitals hospital) {
        this.date = date;
        this.workHours = workHours;
        this.shift = shift;
        this.loggedInTime = loggedInTime;
        this.loggedOutTime = loggedOutTime;
        this.userInStaffManagement = userInStaffManagement;
        this.hospital = hospital;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalTime getLoggedInTime() {
        return loggedInTime;
    }

    public void setLoggedInTime(LocalTime loggedInTime) {
        this.loggedInTime = loggedInTime;
    }

    public LocalTime getLoggedOutTime() {
        return loggedOutTime;
    }

    public void setLoggedOutTime(LocalTime loggedOutTime) {
        this.loggedOutTime = loggedOutTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getWorkHours() {
        return workHours;
    }

    public void setWorkHours(Float workHours) {
        this.workHours = workHours;
    }

    public Users getUserInStaffManagement() {
        return userInStaffManagement;
    }

    public void setUserInStaffManagement(Users userInStaffManagement) {
        this.userInStaffManagement = userInStaffManagement;
    }

    public Hospitals getHospital() {
        return hospital;
    }

    public void setHospital(Hospitals hospital) {
        this.hospital = hospital;
    }
}
