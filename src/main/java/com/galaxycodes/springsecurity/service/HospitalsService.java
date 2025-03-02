package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.HospitalDTO;
import com.galaxycodes.springsecurity.DTOs.HospitalPatientsResponseDTO;
import com.galaxycodes.springsecurity.DTOs.HospitalResponseDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.repo.HospitalsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

        Map<String, String> response = new HashMap<>();
        response.put("message", "Hospital created");

        return ResponseEntity.ok(response);


    }

    private Hospitals toHospital(HospitalDTO dto) {
        var hospital = new Hospitals();
        hospital.setHospitalName(dto.name());
        hospital.setLocation(dto.location());
        return hospital;
    }

    public List<HospitalResponseDTO> getAllHospitals() {
        return hospitalsRepo.findAll()
                .stream()
                .map(this::toHospitalResponseDTO)
                .collect(Collectors.toList());
    }

    private HospitalResponseDTO toHospitalResponseDTO(Hospitals hospital) {
        return new HospitalResponseDTO(hospital.getId(),hospital.getHospitalName(), hospital.getLocation());
    }

    public ResponseEntity<?> getHospital(Integer hospitalID) {
        Optional<Hospitals> h = hospitalsRepo.findById(hospitalID);
        HospitalPatientsResponseDTO hospitalDTO = new HospitalPatientsResponseDTO(
               h.get().getId(),h.get().getHospitalName(),h.get().getPatients()
       );
       return  new ResponseEntity<>(hospitalDTO, HttpStatus.OK);
    }
}
