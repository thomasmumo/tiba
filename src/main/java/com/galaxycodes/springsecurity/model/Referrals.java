package com.galaxycodes.springsecurity.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.Date;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Referrals {
    @Id
    @GeneratedValue
    private Integer id;
    private String referralReason;
    private Date referralDate=new Date();
    @ManyToOne
    @JoinColumn(name = "userInReferrals_id")

    private Users userInReferrals;

    private Integer toStaffId;

    @ManyToOne
    @JoinColumn(name = "hospital_id")

    private Hospitals hospital;
    @ManyToOne
    @JoinColumn(name = "patient_id")

    private Patients patient;

    private Integer to_hospital_id;

    public Referrals() {
    }



    public Referrals(Integer to_hospital_id,Patients patient, Integer toStaffId, String referralReason, Date referralDate, Users userInReferrals, Hospitals hospital) {
        this.referralReason = referralReason;
        this.patient = patient;
        this.to_hospital_id = to_hospital_id;
        this.toStaffId = toStaffId;
        this.referralDate = referralDate;
        this.userInReferrals = userInReferrals;
        this.hospital = hospital;
    }

    public Integer getToStaffId() {
        return toStaffId;
    }

    public void setToStaffId(Integer toStaffId) {
        this.toStaffId = toStaffId;
    }

    public Patients getPatient() {
        return patient;
    }

    public void setPatient(Patients patient) {
        this.patient = patient;
    }

    public Integer getTo_staff_id() {
        return toStaffId;
    }

    public void setTo_staff_id(Integer to_staff_id) {
        this.toStaffId = to_staff_id;
    }

    public Integer getTo_hospital_id() {
        return to_hospital_id;
    }

    public void setTo_hospital_id(Integer to_hospital_id) {
        this.to_hospital_id = to_hospital_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReferralReason() {
        return referralReason;
    }

    public void setReferralReason(String referralReason) {
        this.referralReason = referralReason;
    }

    public Date getReferralDate() {
        return referralDate;
    }

    public void setReferralDate(Date referralDate) {
        this.referralDate = referralDate;
    }

    public Users getUserInReferrals() {
        return userInReferrals;
    }

    public void setUserInReferrals(Users userInReferrals) {
        this.userInReferrals = userInReferrals;
    }

    public Hospitals getHospital() {
        return hospital;
    }

    public void setHospital(Hospitals hospital) {
        this.hospital = hospital;
    }
}
