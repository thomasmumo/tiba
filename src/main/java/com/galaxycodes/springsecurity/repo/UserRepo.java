package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<Users, Integer> {
    Users findByUserName(String username);
    List<Users> findAllByHospitalInUsers_id(Integer hospital_id);
    List<Users> findAllByRole(String role);

}
