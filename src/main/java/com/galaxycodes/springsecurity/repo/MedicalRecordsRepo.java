package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.MedicalRecords;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MedicalRecordsRepo extends JpaRepository<MedicalRecords, Integer> {

    List<MedicalRecords> findAllByPatient_id(Integer patient_id);
    List<MedicalRecords> findAllByHospital_id(Integer hospital_id);


}
