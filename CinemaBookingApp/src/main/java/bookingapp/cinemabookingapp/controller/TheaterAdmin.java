package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.dtos.request.CreateTheaterRequest;
import bookingapp.cinemabookingapp.service.interfaces.TheaterAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Admin/TheaterAdmin")
public class TheaterAdmin {
    @Autowired
    TheaterAdminService theaterAdminService;

    @PostMapping("/CreateTheater")
    public ResponseEntity<?> createTheater(@RequestBody CreateTheaterRequest createTheaterRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(theaterAdminService.createTheater(createTheaterRequest));
    }

}
