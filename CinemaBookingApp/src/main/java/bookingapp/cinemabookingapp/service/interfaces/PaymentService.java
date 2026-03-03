package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentResultRequest;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;

import java.io.IOException;
import java.net.URISyntaxException;

public interface PaymentService {
    PaymentResponse pay(PaymentRequest paymentRequest) throws IOException, InterruptedException;

    Object ListenToPaymentResult(PaymentResultRequest paymentResultRequest, String signature);
}
