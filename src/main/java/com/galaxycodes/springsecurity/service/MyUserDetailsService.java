package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.model.PatientPrincipal;
import com.galaxycodes.springsecurity.model.Patients;
import com.galaxycodes.springsecurity.model.UserPrincipal;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.PatientRepo;
import com.galaxycodes.springsecurity.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PatientRepo patientRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Users user = userRepo.findByUserName(username);
        Patients patient = patientRepo.findByUserName(username);

        if(user == null) {
            System.out.println("user not found");

            if(patient == null) {
                System.out.println("Patient not found");
                throw new UsernameNotFoundException("Patient not found");

            }
            return new PatientPrincipal(patient);

        }

        return new UserPrincipal(user);
    }
}
