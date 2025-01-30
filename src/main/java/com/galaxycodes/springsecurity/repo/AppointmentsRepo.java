package com.galaxycodes.springsecurity.repo;

import com.galaxycodes.springsecurity.model.Appointments;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentsRepo extends JpaRepository<Appointments, Integer> {


    List<Appointments> findAllByPatient_id(Integer patient_id);
    List<Appointments> findAllByUserInAppointment_id(Integer staff_id);
}
