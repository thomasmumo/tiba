package com.galaxycodes.springsecurity.service;

import com.galaxycodes.springsecurity.DTOs.UpdateUserDTO;
import com.galaxycodes.springsecurity.DTOs.UsersDTO;
import com.galaxycodes.springsecurity.model.Hospitals;
import com.galaxycodes.springsecurity.model.StaffManagement;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.StaffManagementRepo;
import com.galaxycodes.springsecurity.repo.UserRepo;
import com.galaxycodes.springsecurity.utils.ImagesUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UsersService {


        @Autowired
        private UserRepo usersRepo;

        @Autowired
        private AuthenticationManager authenticationManager;

        @Autowired
        private JWTService jwtService;

        @Autowired
        private StaffManagementRepo staffManagementRepo;

        private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

        public ResponseEntity<?> createUser(UsersDTO dto) throws IOException {
            if(usersRepo.findByUserName(dto.userName()) != null) {
                return new ResponseEntity<>("Username already exists", HttpStatus.CONFLICT);
            }

            var user = toUser(dto);
            usersRepo.save(user);
            return new ResponseEntity<>("User created", HttpStatus.CREATED);
        }


        private Users toUser(UsersDTO dto) throws IOException {


            var user = new Users();
            user.setFirstName(dto.firstName());
            user.setLastName(dto.lastName());
            user.setUserName(dto.userName());
            user.setPassword(encoder.encode(dto.password()));
            user.setPhoneNumber(dto.phoneNumber());
            user.setEmail(dto.email());

            user.setRole(dto.role());

            var hospital = new Hospitals();
            hospital.setId(dto.hospitalId());
            user.setHospitalInUsers(hospital);
            return user;


        }
        @Transactional
        public byte[] downloadProfile(String username) throws IOException {
            Users user= usersRepo.findByUserName(username);


            byte[] image = ImagesUtil.decompressImage(user.getProfileImageData());
            return image;
        }
        public List<Users> getUsers(){

            return usersRepo.findAll();
        }

        public ResponseEntity<?> login(Users user) {
            Users userr = usersRepo.findByUserName(user.getUserName());
            if (userr == null) {return  new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);}

            try {
                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword()));

                if (authentication.isAuthenticated()) {

                    if(usersRepo.findByUserName(user.getUserName()).isActive()) {
                        var staff = usersRepo.findByUserName(user.getUserName());
                        var staffRecord = new StaffManagement();
                        staffRecord.setUserInStaffManagement(staff);
                        staffRecord.setHospital(staff.getHospitalInUsers());
                        staffRecord.setLoggedInTime(LocalTime.now());
                        staffManagementRepo.save(staffRecord);

                        Map<String, String> response = new HashMap<>();
                        response.put("token", jwtService.generateToken(user.getUserName()));
                        response.put("from","users");
                        return ResponseEntity.ok(response);
                    }
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Your account is deactivated. Please contact your system administrator.");

                }
            }catch (AuthenticationException exception){return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Email or password");}
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");

        }

        public ResponseEntity<String> deactivateUser(String username){
            Users user = usersRepo.findByUserName(username);
            user.setActive(false);
            usersRepo.save(user);
            return ResponseEntity.ok("account deactivated");
        }
        public ResponseEntity<String> activateUser(String username){
            Users user = usersRepo.findByUserName(username);
            user.setActive(true);
            usersRepo.save(user);
            return ResponseEntity.ok("account activated");
        }

    @Transactional
    public ResponseEntity<?> updateProfile(String username, MultipartFile file) throws IOException {
            Users user = usersRepo.findByUserName(username);
            user.setProfileImageData(ImagesUtil.compressImage(file.getBytes()));
            usersRepo.save(user);
            return ResponseEntity.ok("profile picture updated");

    }

    public ResponseEntity<?> getUsersByUsername(String username) {
            var user = usersRepo.findByUserName(username);
            if(user == null) {
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<?> getUsersByHospitalId(Integer hospitalId) {
            List<Users> users = usersRepo.findAllByHospitalInUsers_id(hospitalId);
            if(users.isEmpty()) {
                return new ResponseEntity<>("users not found", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(users, HttpStatus.FOUND);
    }

    public ResponseEntity<?> deleteUser(String username) {
            var user = usersRepo.findByUserName(username);
            if(user == null) {
                return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
            }
            usersRepo.delete(user);
            return ResponseEntity.ok("User deleted successfully");
    }

    public ResponseEntity<?> updateUserDetails(String username,UpdateUserDTO dto) {
        var user = usersRepo.findByUserName(username);
        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail(dto.email());
        user.setPhoneNumber(dto.phoneNumber());
        user.setRole(dto.role());
        user.setPassword(encoder.encode(dto.password()));
        usersRepo.save(user);
        return ResponseEntity.ok("User updated successfully");
    }


    public ResponseEntity<String> logout(Integer staffId) {
            var staffRecord = staffManagementRepo.findAllByDate(LocalDate.now())
                    .stream()
                    .filter(record -> record.getLoggedOutTime()==null && record.getUserInStaffManagement().getId().equals(staffId))
                    .collect(Collectors.toList()).get(0);
            staffRecord.setLoggedOutTime(LocalTime.now());
            var minsNow = LocalTime.now().getMinute();
            if (staffRecord.getLoggedInTime().getMinute() > minsNow) {
                staffRecord.setWorkHours(
                        ((((float)LocalTime.now().getHour() - (float)staffRecord.getLoggedInTime().getHour())*60) +
                                ((float)staffRecord.getLoggedInTime().getMinute() - (float)minsNow))/60
                );
            }
            staffRecord.setWorkHours(
                    ((((float)LocalTime.now().getHour() - (float)staffRecord.getLoggedInTime().getHour())*60) +
                            ( (float)minsNow - (float)staffRecord.getLoggedInTime().getMinute()))/60
            );
            staffManagementRepo.save(staffRecord);
            return ResponseEntity.ok("logged out successfully");
    }
}
