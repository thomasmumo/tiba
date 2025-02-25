package com.galaxycodes.springsecurity.controller;

import com.galaxycodes.springsecurity.DTOs.AdminResponseDTO;
import com.galaxycodes.springsecurity.DTOs.ChangePasswordDTO;
import com.galaxycodes.springsecurity.DTOs.UpdateUserDTO;
import com.galaxycodes.springsecurity.DTOs.UsersDTO;
import com.galaxycodes.springsecurity.model.Users;
import com.galaxycodes.springsecurity.repo.UserRepo;
import com.galaxycodes.springsecurity.service.JWTService;
import com.galaxycodes.springsecurity.service.UsersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
@CrossOrigin(origins = "*")
//@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UsersController {

    @Autowired
    private UsersService usersService;

    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return usersService.getUsers();

    }
    @GetMapping("/users/{userName}/get-user")
    public ResponseEntity<?> getUsersByUsername(@PathVariable("userName") String username) {
        return usersService.getUsersByUsername(username);

    }
    @GetMapping("/users/{hospital_id}/get-users")
    public ResponseEntity<?> getUsersByHospitalId(@PathVariable("hospital_id") Integer hospitalId) {
        return usersService.getUsersByHospitalId(hospitalId);

    }
    @DeleteMapping("/users/{user-name}/delete-user")
    public ResponseEntity<?> deleteUser(@Valid @PathVariable("user-name") String username){
        return usersService.deleteUser(username);
    }
    @PutMapping("/users/{user-name}/update-user-details")
    public ResponseEntity<?> updateUserDetails( @PathVariable("user-name") String username,@Valid @RequestBody UpdateUserDTO dto){
        return  usersService.updateUserDetails(username,dto);
    }
    @PutMapping("/users/{user-name}/change-password")
    public ResponseEntity<?> changePassword( @PathVariable("user-name") String username,@Valid @RequestBody ChangePasswordDTO dto){
        return  usersService.changePassword(username,dto);
    }
    @PutMapping("/users/{user-name}/SuperAdmin-change-password")
    public ResponseEntity<?> SuperAdminChangePassword( @PathVariable("user-name") String username,@Valid @RequestBody ChangePasswordDTO dto){
        return  usersService.superAdminChangePassword(username,dto);
    }

    @PostMapping("/users/create-user")
    public ResponseEntity<?> createUser(@Valid @RequestBody UsersDTO dto) throws IOException {

        return usersService.createUser(dto);
    }

    @PostMapping("/users/user-login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        return usersService.login(user);

    }
    @PostMapping("/users/{staff-id}/logout")
    public ResponseEntity<?> login(@PathVariable("staff-id" )Integer staffId) {
        return usersService.logout(staffId);

    }
    @PutMapping("/users/{user-name}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable("user-name") String userName) {
        return usersService.deactivateUser(userName);
    }
    @PutMapping("/users/{user-name}/activate")
    public ResponseEntity<?> activateUser(@PathVariable("user-name") String userName) {
        return usersService.activateUser(userName);
    }

    @GetMapping("/users/{userName}/get-profile-pic")
    public ResponseEntity<?> downloadProfile(@PathVariable(value = "userName") String userName) throws IOException {
        return this.usersService.downloadProfile(userName);
    }

    @PutMapping("/users/{userName}/update-profile-pic")
    public ResponseEntity<?> updateProfile(@RequestParam("file") MultipartFile file,@PathVariable("userName") String username) throws IOException {
        return usersService.updateProfile(username,file);
    }

    @PutMapping("/users/user/{userId}/assign-shift/{shift}")
    public ResponseEntity<?> assignShift(@PathVariable("shift") String shift,@PathVariable("userId") Integer userId) {
        return usersService.assignShift(shift,userId);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException exp) {
        var errors =  new HashMap<String, String>();

        exp.getBindingResult().getAllErrors().forEach(error -> {
            var fieldName = ((FieldError) error).getField();
            var errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}
