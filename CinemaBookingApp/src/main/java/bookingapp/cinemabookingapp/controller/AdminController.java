package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.dtos.request.*;
import bookingapp.cinemabookingapp.service.interfaces.Auth;
import bookingapp.cinemabookingapp.service.interfaces.SuperAdminService;
import bookingapp.cinemabookingapp.service.interfaces.TheaterAdminService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Admin")
public class AdminController {

    @Autowired
    private SuperAdminService superAdminService;

    @Autowired
    TheaterAdminService theaterAdminService;

    @Autowired
    Auth auth;


    @PostMapping("/SuperAdmin/CreateAdmin")
    public ResponseEntity<?> createTheaterAdmin(@Valid @RequestBody  CreateAdminRequest   createAdminRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(superAdminService.createTheaterAdmin(createAdminRequest));
    }


    @PostMapping("/Login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.status(HttpStatus.OK).body(auth.login(loginRequest));
    }

    @PostMapping("/TheaterAdmin/CreateTheater")
    public ResponseEntity<?> createTheater(@Valid @RequestBody CreateTheaterRequest createTheaterRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterAdminService.createTheater(createTheaterRequest));
    }


    @PostMapping("/TheaterAdmin/CreateShow")
    public ResponseEntity<?> createShow(@Valid @RequestBody CreateShowManagerRequest createShowManagerRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterAdminService.createShow(createShowManagerRequest));
    }

    @PostMapping("/TheaterAdmin/CreateMovie")
    public ResponseEntity<?> createMovie(@Valid @RequestBody CreateMovieRequest createMovieRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterAdminService.createMovie(createMovieRequest));
    }

    @PostMapping("/TheaterAdmin/AddShowToTheater")
    public ResponseEntity<?> addShowToTheater(@Valid @RequestBody AddShowTheaterRequest addShowTheaterRequest){
        return ResponseEntity.status(HttpStatus.OK).body(theaterAdminService.addShowToTheater(addShowTheaterRequest));
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

}
