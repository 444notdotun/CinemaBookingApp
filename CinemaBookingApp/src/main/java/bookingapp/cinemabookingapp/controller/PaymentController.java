package bookingapp.cinemabookingapp.controller;

import bookingapp.cinemabookingapp.dtos.request.PaymentResultRequest;
import bookingapp.cinemabookingapp.service.interfaces.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/paymentApi")
public class PaymentController {
    @Autowired
    PaymentService paymentService;

    @PostMapping
    public ResponseEntity<?> listenToPaymentResult(@RequestBody PaymentResultRequest paymentResultRequest ,@RequestHeader("x-paystack-signature") String signature){
        return  ResponseEntity.status(HttpStatus.OK).body(paymentService.ListenToPaymentResult(paymentResultRequest,signature));
    }
}
