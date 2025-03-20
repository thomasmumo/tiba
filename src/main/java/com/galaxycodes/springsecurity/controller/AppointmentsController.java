package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.AppointmentsDTO;
import com.galaxycodes.springsecurity.DTOs.AppointmentsResponseDTO;
import com.galaxycodes.springsecurity.model.Appointments;
import com.galaxycodes.springsecurity.service.AppointmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class AppointmentsController {
    @Autowired
    private AppointmentsService appointmentsService;


    @PostMapping("/appointments/{doctor-id}/create-appointment")
    public ResponseEntity<?> createAppointment(@PathVariable("doctor-id") Integer doctorId, @RequestBody AppointmentsDTO dto) {
        return appointmentsService.createAppointment(doctorId,dto);

    }

    @GetMapping("/appointments/{doctor-id}/doctor-get-appointment")
    public List<AppointmentsResponseDTO> getAppointmentByDoctor(@PathVariable("doctor-id") Integer doctorId) {
        return appointmentsService.getAppointmentByDoctor(doctorId);
    }
    @GetMapping("/appointments/{patient-id}/patient-get-appointment")
    public List<AppointmentsResponseDTO> getAppointmentByPatient(@PathVariable("patient-id") Integer patientId) {
        return appointmentsService.getAppointmentByPatient(patientId);
    }

    @PostMapping("/appointments/{patient-id}/book-appointment")
    public ResponseEntity<?> bookAppointment(@PathVariable("patient-id") Integer patientId, @RequestBody AppointmentsDTO dto) {
        return appointmentsService.bookAppointment(patientId, dto);
    }

    @PutMapping("/appointments/{patient-id}/complete-appointment")
    public ResponseEntity<?> acceptAppointment(@PathVariable("patient-id") Integer patientId) {
        return appointmentsService.acceptAppointment(patientId);
    }
    @PutMapping("/appointments/{patient-id}/cancel-appointment")
    public ResponseEntity<?> cancelAppointment(@PathVariable("patient-id") Integer patientId) {
        return appointmentsService.cancelAppointment(patientId);
    }
    @DeleteMapping("/appointments/delete-all")
    public ResponseEntity<?> deleteAllAppointments() {
        return appointmentsService.deleteAllAppointments();
    }
}
