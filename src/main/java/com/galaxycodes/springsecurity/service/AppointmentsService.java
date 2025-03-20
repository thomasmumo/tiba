package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.*;
import com.galaxycodes.springsecurity.model.Appointments;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.AppointmentsRepo;
import com.galaxycodes.springsecurity.repo.PatientRepo;
import com.galaxycodes.springsecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AppointmentsService {

    @Autowired
    private AppointmentsRepo appointmentsRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PatientRepo patientRepo;
    public ResponseEntity<?> createAppointment(Integer doctorId, AppointmentsDTO dto) {
        var appointment = toAppointment(doctorId,dto);
        var patientAppointment = appointmentsRepo.findAllByPatient_id(dto.patientID())
                .stream()
                .filter(app -> app.getAppointmentStatus().equals("Active") && app.getUserInAppointment().getId().equals(doctorId) && app.getAppointmentDate().equals(appointment.getAppointmentDate()))
                .collect(Collectors.toList());
        if(!patientAppointment.isEmpty()) {
            return new ResponseEntity<>(patientRepo.findById(dto.patientID()).get().getFirstName()+" already has an appointment scheduled at "+dto.appointmentDate(), HttpStatus.CONFLICT);
        }

        appointmentsRepo.save(appointment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment created successfully");

        return ResponseEntity.ok(response);



    }

    private Appointments toAppointment(Integer doctorId, AppointmentsDTO dto) {
        var appointment = new Appointments();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate appointmentDate = LocalDate.parse(dto.appointmentDate(), formatter);
        LocalTime appointmentTime = LocalTime.parse(dto.appointmentTime());

        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setAppointmentReason(dto.appointmentReason());

        var hospital = new Hospitals();
        hospital.setId(dto.hospitalID());
        appointment.setHospital(hospital);

        var doctor = new Users();
        doctor.setId(doctorId);
        appointment.setUserInAppointment(doctor);

        var patients = new Patients();
        patients.setId(dto.patientID());
        appointment.setPatient(patients);



        return appointment;
    }

    public List<AppointmentsResponseDTO> getAppointmentByDoctor(Integer doctorId) {
        var appointments = appointmentsRepo.findAllByUserInAppointment_id(doctorId);
        return appointments.stream()
                .map(appointment -> new AppointmentsResponseDTO(
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentReason(),
                        appointment.getAppointmentStatus(),
                        appointment.getAppointmentTime(),
                        new HospitalResponseDTO(appointment.getHospital().getId(),appointment.getHospital().getHospitalName(),appointment.getHospital().getLocation(),appointment.getHospital().getUsers()),
                        new MiniPatientResponseDTO(appointment.getPatient().getId(),appointment.getPatient().getFirstName(),appointment.getPatient().getLastName(),appointment.getPatient().getUserName()),
                        new DoctorResponseDTO(appointment.getUserInAppointment().getId(),appointment.getUserInAppointment().getFirstName(),appointment.getUserInAppointment().getLastName(),appointment.getUserInAppointment().getUserName())


                ))
                .toList();
    }

    public List<AppointmentsResponseDTO> getAppointmentByPatient(Integer patientId) {
        var appointments = appointmentsRepo.findAllByPatient_id(patientId);
        return appointments.stream()
                .map(appointment -> new AppointmentsResponseDTO(
                        appointment.getAppointmentDate(),
                        appointment.getAppointmentReason(),
                        appointment.getAppointmentStatus(),
                        appointment.getAppointmentTime(),
                        new HospitalResponseDTO(appointment.getHospital().getId(),appointment.getHospital().getHospitalName(),appointment.getHospital().getLocation(),appointment.getHospital().getUsers()),
                        new MiniPatientResponseDTO(appointment.getPatient().getId(),appointment.getPatient().getFirstName(),appointment.getPatient().getLastName(),appointment.getPatient().getUserName()),
                        new DoctorResponseDTO(appointment.getUserInAppointment().getId(),appointment.getUserInAppointment().getFirstName(),appointment.getUserInAppointment().getLastName(),appointment.getUserInAppointment().getUserName())


                ))
                .toList();

    }

    public ResponseEntity<?> bookAppointment(Integer patientId, AppointmentsDTO dto) {

        var appointment = toAppointmentByPatient(patientId,dto);
        var patientAppointments = appointmentsRepo.findAllByPatient_id(patientId)
                .stream()
                .filter(app -> (app.getAppointmentStatus().equals("Active") || app.getAppointmentStatus().equals("Booked")) && app.getAppointmentDate().equals(appointment.getAppointmentDate()) && app.getUserInAppointment().getId().equals(dto.doctorID()))
                .collect(Collectors.toList());

        if(!patientAppointments.isEmpty()) {
            return new ResponseEntity<>("You already have an active or booked appointment with doctor "+userRepo.findById(dto.doctorID()).get().getFirstName()+" on "+appointment.getAppointmentDate(), HttpStatus.OK);
        }
        var doctorAppointments = appointmentsRepo.findAllByUserInAppointment_id(appointment.getUserInAppointment().getId()).stream()
                .filter(app -> app.getAppointmentTime().getHour()==appointment.getAppointmentTime().getHour() && (app.getAppointmentStatus().equals("Active") ||app.getAppointmentStatus().equals("Booked")) && app.getAppointmentDate().equals(appointment.getAppointmentDate()))  // Lambda to filter adults
                .collect(Collectors.toList());
        if (!doctorAppointments.isEmpty()) {
            return new ResponseEntity<>("Doctor is booked at "+appointment.getAppointmentTime().getHour()+". Please choose different time", HttpStatus.OK);
        }


        appointmentsRepo.save(appointment);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment booked successfully");

        return ResponseEntity.ok(response);

    }

    private Appointments toAppointmentByPatient(Integer patientId, AppointmentsDTO dto) {
        var appointment = new Appointments();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate appointmentDate = LocalDate.parse(dto.appointmentDate(), formatter);
        LocalTime appointmentTime = LocalTime.parse(dto.appointmentTime());
        appointment.setAppointmentDate(appointmentDate);
        appointment.setAppointmentTime(appointmentTime);
        appointment.setAppointmentReason(dto.appointmentReason());
        appointment.setAppointmentStatus("Booked");

        var hospital = new Hospitals();
        hospital.setId(dto.hospitalID());
        appointment.setHospital(hospital);

        var doctor = new Users();
        doctor.setId(dto.doctorID());
        appointment.setUserInAppointment(doctor);

        var patients = new Patients();
        patients.setId(patientId);
        appointment.setPatient(patients);



        return appointment;
    }


    public ResponseEntity<?> deleteAllAppointments() {
        appointmentsRepo.deleteAll();
        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointments deleted successfully");

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> acceptAppointment(Integer patientId) {
        var appointments = appointmentsRepo.findAllByUserInAppointment_id(patientId).stream()
                .filter(app -> app.getAppointmentStatus().equals("Booked"))  // Lambda to filter adults
                .collect(Collectors.toList());
        var appointment = appointments.get(0);
        appointment.setAppointmentStatus("Complete");
        appointmentsRepo.save(appointment);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment accepted");

        return ResponseEntity.ok(response);

    }

    public ResponseEntity<?> cancelAppointment(Integer patientId) {
        var appointments = appointmentsRepo.findAllByUserInAppointment_id(patientId).stream()
                .filter(app -> app.getAppointmentStatus().equals("Booked"))  // Lambda to filter adults
                .collect(Collectors.toList());
        var appointment = appointments.get(0);
        appointment.setAppointmentStatus("Canceled");
        appointmentsRepo.save(appointment);

        Map<String, String> response = new HashMap<>();
        response.put("message", "Appointment accepted");

        return ResponseEntity.ok(response);
    }
}
