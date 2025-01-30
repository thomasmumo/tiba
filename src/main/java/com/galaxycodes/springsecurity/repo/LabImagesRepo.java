package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.LabImages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabImagesRepo extends JpaRepository<LabImages, Integer> {
    List<LabImages> findAllByHospitalInImages_id(Integer labId);
    List<LabImages> findAllByUserInImages_id(Integer staffId);
    List<LabImages> findAllByPatientInImages_id(Integer patientId);
}
