package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.StaffManagement;
import com.galaxycodes.springsecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StaffManagementRepo extends JpaRepository<StaffManagement, Integer> {
    List<StaffManagement> findAllByHospital_id(Integer hospital_id);
    List<StaffManagement> findAllByUserInStaffManagement_id(Integer staff_id);
    List<StaffManagement> findAllByDate(LocalDate date);
}
