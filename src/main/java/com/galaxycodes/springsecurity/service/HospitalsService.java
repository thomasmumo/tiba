package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.HospitalDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.repo.HospitalsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class HospitalsService {
    @Autowired
    private HospitalsRepo hospitalsRepo;

    public ResponseEntity<?> createHospital(HospitalDTO dto) {
        var hospital = toHospital(dto);

        if(hospitalsRepo.findByHospitalName(dto.name())!=null){
            return new ResponseEntity<>("Hospital already exists", HttpStatus.CONFLICT);
        }
        hospitalsRepo.save(hospital);


        return new  ResponseEntity<>("Hospital created", HttpStatus.CREATED);
    }

    private Hospitals toHospital(HospitalDTO dto) {
        var hospital = new Hospitals();
        hospital.setHospitalName(dto.name());
        hospital.setLocation(dto.location());
        return hospital;
    }

    public List<HospitalDTO> getAllHospitals() {
        return hospitalsRepo.findAll()
                .stream()
                .map(this::toHospitalDTO)
                .collect(Collectors.toList());
    }

    private HospitalDTO toHospitalDTO(Hospitals hospital) {
        return new HospitalDTO(hospital.getHospitalName(), hospital.getLocation());
    }
}
