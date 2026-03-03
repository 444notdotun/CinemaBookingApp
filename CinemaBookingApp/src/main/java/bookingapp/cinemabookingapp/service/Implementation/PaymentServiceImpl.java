package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentResultRequest;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;
import bookingapp.cinemabookingapp.service.interfaces.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    BookingRepository  bookingRepository;

    @Value("${app.payStack.secret}")
    private String payStackSecretKey;
    @Autowired
    IdGeneratorServices   idGeneratorServices;
    @Autowired
    ObjectMapper objectMapper;
    @Value("${app.allowedIps}")
    private List<String> listOfIps;

    @Override
    public PaymentResponse pay(PaymentRequest paymentRequest) throws IOException, InterruptedException {
        String eMail = loadUsername(paymentRequest);
        log.info("Payment request received for username {}",eMail);

        Map<String, Object> jsonBody = getStringObjectMap(paymentRequest, eMail);
        URI url = URI.create("https://api.paystack.co/transaction/initialize");

        String jsonPayload = objectMapper.writeValueAsString(jsonBody);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(url)
                .header("content_type","application/json")
                .header("Authorization","Bearer "+payStackSecretKey)
                .POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
                .build();
        HttpResponse<String> response = client.send(request,HttpResponse.BodyHandlers.ofString());
        log.info("Response from PayStack API is {}", response.body());
        return objectMapper.readValue(response.body(),PaymentResponse.class);
    }

    @Override
    public Object ListenToPaymentResult(PaymentResultRequest paymentResultRequest, String signature) {
        validateHeader(signature);



    }

    private void validateHeader(String signature) {
        if(!listOfIps.contains(signature)){
            throw new RuntimeException("Invalid signature");
        }
    }


    private @NonNull Map<String, Object> getStringObjectMap(PaymentRequest paymentRequest, String eMail) {
        Map<String,Object> jsonBody = new HashMap<>();
        jsonBody.put("email", eMail);
        jsonBody.put("amount", paymentRequest.getPrice());
        jsonBody.put("reference",loadBookingId(paymentRequest));
        return jsonBody;
    }

    private String loadUsername(PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("booking not made!"));

        return booking.getUserName();
    }

    private String loadBookingId(PaymentRequest paymentRequest) {
        Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
                .orElseThrow(() -> new RuntimeException("booking not made!"));

        return booking.getPaymentId();
    }

}
