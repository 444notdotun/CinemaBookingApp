package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.dtos.request.LoginRequest;
import bookingapp.cinemabookingapp.service.interfaces.Auth;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Admin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @Autowired
    Auth auth;

    @PostMapping("/CreateAdmin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody CreateAdminRequest   createAdminRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.createTheaterAdmin(createAdminRequest));
    }
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(auth.login(loginRequest));
    }

}
