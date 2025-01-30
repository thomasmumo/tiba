package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Patients;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientRepo extends JpaRepository<Patients, Integer> {
    Patients findByUserName(String userName);
}
