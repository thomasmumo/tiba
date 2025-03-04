package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Hospitals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HospitalsRepo extends JpaRepository<Hospitals, Integer> {
    Hospitals findByHospitalName(String name);






}
