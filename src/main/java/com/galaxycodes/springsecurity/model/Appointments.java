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
public class Appointments {
    @Id
    @GeneratedValue
    private Integer id;
    private String appointmentReason;
    private LocalDate appointmentDate;
    private LocalTime appointmentTime;
    private String appointmentStatus = "Active";
    @ManyToOne
    @JoinColumn(name = "userInAppointment_id")

    private Users userInAppointment;

    @ManyToOne
    @JoinColumn(name = "hospital_id")

    private Hospitals hospital;

    @ManyToOne
    @JoinColumn(name = "patient_id")

    private Patients patient;

    public Appointments() {
    }

    public Appointments(String appointmentReason, LocalDate appointmentDate, LocalTime appointmentTime, String appointmentStatus, Users userInAppointment, Hospitals hospital, Patients patient) {
        this.appointmentReason = appointmentReason;
        this.appointmentDate = appointmentDate;
        this.appointmentTime = appointmentTime;
        this.appointmentStatus = appointmentStatus;
        this.userInAppointment = userInAppointment;
        this.hospital = hospital;
        this.patient = patient;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppointmentReason() {
        return appointmentReason;
    }

    public void setAppointmentReason(String appointmentReason) {
        this.appointmentReason = appointmentReason;
    }

    public LocalDate getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(LocalDate appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public LocalTime getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(LocalTime appointmentTime) {
        this.appointmentTime = appointmentTime;
    }

    public String getAppointmentStatus() {
        return appointmentStatus;
    }

    public void setAppointmentStatus(String appointmentStatus) {
        this.appointmentStatus = appointmentStatus;
    }

    public Users getUserInAppointment() {
        return userInAppointment;
    }

    public void setUserInAppointment(Users userInAppointment) {
        this.userInAppointment = userInAppointment;
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
