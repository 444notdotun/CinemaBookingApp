package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.data.models.SuperAdmin;
import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/SuperAdmin")
public class SuperAdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @PostMapping("/CreateAdmin")
    public ResponseEntity<?> createSuperAdmin(@RequestBody CreateAdminRequest   createAdminRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.createTheaterAdmin(createAdminRequest));
    }

}
