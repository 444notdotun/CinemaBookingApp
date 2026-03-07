package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.service.interfaces.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("user/")
public class UserController {
    @Autowired
    TheaterService theaterService;
    @Autowired
    ShowService  showService;
    @Autowired
    BookingService  bookingService;
    @Autowired
    PaymentService  paymentService;
    
    
    @GetMapping("/getAllTheater")
    public ResponseEntity<?> getAllTheater(){
        return ResponseEntity.status(HttpStatus.FOUND).body(theaterService.GetAllTheater());
    }

    @GetMapping("/getAllShows/{id}")
    public ResponseEntity<?> getAllShow(@PathVariable  String id){
        return ResponseEntity.status(HttpStatus.FOUND).body(theaterService.getAllShows(id));
    }
    
    @GetMapping("/getShow/{id}")
    public  ResponseEntity<?> getShow(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.FOUND).body(showService.getShow(id));
    }
    @GetMapping("/showDescription/{id}")
    public ResponseEntity<?> getShowDescription(@PathVariable String id){
        return ResponseEntity.status(HttpStatus.FOUND).body(showService.ShowDescription(id));
    }

    @PostMapping("/bookAShow")
    public ResponseEntity<?> bookAShow(@RequestBody BookShowRequest bookShowRequest){
        return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.bookShow(bookShowRequest));
    }

    @GetMapping("/generateReceipt/{id}")
    public ResponseEntity<?> generateReceipt(@PathVariable  String id){
        return ResponseEntity.status(HttpStatus.OK).body(bookingService.generateReceipt(id));
    }

    @PostMapping("/payment")
    public ResponseEntity<?> payment(@RequestBody PaymentRequest paymentRequest) throws IOException, InterruptedException {
        return ResponseEntity.status(HttpStatus.CREATED).body(paymentService.pay(paymentRequest));
    }

}
