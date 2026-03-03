package bookingapp.cinemabookingapp.controller;

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
    public ResponseEntity<?> listenToPaymentResult(@RequestBody String paymentResultRequest ,@RequestHeader("x-paystack-signature") String signature){
        paymentService.listenToPaymentResult(paymentResultRequest,signature);
        return  ResponseEntity.status(HttpStatus.OK).build();
    }
}
