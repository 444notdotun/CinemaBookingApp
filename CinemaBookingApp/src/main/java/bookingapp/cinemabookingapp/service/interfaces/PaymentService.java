package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;

import java.io.IOException;

public interface PaymentService {
    PaymentResponse pay(PaymentRequest paymentRequest) throws IOException, InterruptedException;

    void listenToPaymentResult(String paymentResultRequest, String signature);
}
