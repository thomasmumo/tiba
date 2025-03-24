package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.*;
import com.galaxycodes.springsecurity.repo.StaffManagementRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffManagementService {
    @Autowired
    private StaffManagementRepo staffRepo;

    public List<StaffManagementResponseDTO> getHospitalRecords(Integer hospitalId) {
        var managementRecords=staffRepo.findAllByHospital_id(hospitalId);
        return managementRecords.stream()
                .map(rec -> new StaffManagementResponseDTO(
                        rec.getDate(),
                        rec.getId(),
                        rec.getLoggedInTime(),
                        rec.getLoggedOutTime(),
                        rec.getShift(),
                        new UsersManagementResponseDTO(rec.getUserInStaffManagement().getFirstName(),rec.getUserInStaffManagement().getLastName(),rec.getUserInStaffManagement().getUserName(),rec.getUserInStaffManagement().getRole(),rec.getUserInStaffManagement().isAcceptreferrals(),rec.getUserInStaffManagement().getPhoneNumber(),rec.getUserInStaffManagement().getEmail(),rec.getUserInStaffManagement().isLoggedIn(),rec.getUserInStaffManagement().getProfileURL()),
                        rec.getWorkHours()

                ))
                .toList();
    }
}
