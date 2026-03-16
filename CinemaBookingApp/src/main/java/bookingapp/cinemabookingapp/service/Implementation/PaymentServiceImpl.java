package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.PaymentStatus;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.request.PaystackWebhookDTO;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;
import bookingapp.cinemabookingapp.exceptions.BookingNotFound;
import bookingapp.cinemabookingapp.exceptions.validatePaymentHeader;
import bookingapp.cinemabookingapp.service.interfaces.EmailServices;
import bookingapp.cinemabookingapp.service.interfaces.PaymentService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {
    @Autowired
    BookingRepository  bookingRepository;

    @Value("${app.payStack.secret}")
    private String payStackSecretKey;
    @Autowired
    EmailServices emailServices;
    @Autowired
    ObjectMapper objectMapper;


    @Override
    public PaymentResponse pay(String  bookingId) throws IOException, InterruptedException {
        Booking booking = loadUsername(bookingId);
        PaymentRequest paymentRequest =Mapper.mapBookingToRequest(booking);
        paymentRequest.setPrice(paymentRequest.getPrice().multiply(BigDecimal.valueOf(100)));
        log.info("Payment request received for username {}",paymentRequest.getEmail());

        Map<String, Object> jsonBody = getStringObjectMap(paymentRequest);
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
    public void listenToPaymentResult(String payload, String signature) {
        validateHeader(payload, signature);
        log.info("Webhook signature validated");
        PaystackWebhookDTO dto = parsePayload(payload);
        log.info("Paystack Webhook received"+dto.getEvent());
        String reference = dto.getData().getReference();
        Booking booking = bookingRepository
                .findByPaymentId(reference)
                .orElseThrow(() ->
                        new BookingNotFound("Payment not found"));
        if (Idempotent(booking)) return;
        booking.setPaymentStatus(DeterminePaymentStatus(dto.getEvent()));
        booking.setPaymentTime(LocalTime.now());
        bookingRepository.save(booking);
        if(dto.getEvent().equals("charge.success")){
            emailServices.sendTicketEmail(Mapper.createSenderRequest(booking.getUserName(),booking.getId()));
            log.info("Booking {} marked as PAID", reference);
        }else {
            log.info("Booking {} marked as Unpaid", reference);
        }

    }

    private static boolean Idempotent(Booking booking) {
        if (booking.getPaymentStatus() == PaymentStatus.PAYMENT_SUCCESS) {
            log.info("Booking already marked as PAID");
            return true;
        }
        return false;
    }

    private PaystackWebhookDTO parsePayload(String payload) {
        try {
            return objectMapper.readValue(payload, PaystackWebhookDTO.class);
        } catch (Exception e) {
            throw new RuntimeException("Invalid webhook payload");
        }
    }

    private void validateHeader(String payload, String signature) {
        try {
            Mac sha512Hmac = Mac.getInstance("HmacSHA512");
            SecretKeySpec keySpec =
                    new SecretKeySpec(payStackSecretKey.getBytes(), "HmacSHA512");
            sha512Hmac.init(keySpec);
            byte[] hash = sha512Hmac.doFinal(payload.getBytes());
            String computedSignature = bytesToHex(hash);
            if (!computedSignature.equals(signature)) {
                throw new validatePaymentHeader("Invalid signature");
            }
        } catch (Exception e) {
            throw new validatePaymentHeader("Signature validation failed");
        }
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    private PaymentStatus DeterminePaymentStatus(String paymentRequest){
        switch (paymentRequest){
            case "charge.success", "transfer.success" -> {
                return   PaymentStatus.PAYMENT_SUCCESS;
            }
            default -> {return PaymentStatus.PAYMENT_FAILED;}
        }
    }


    private @NonNull Map<String, Object> getStringObjectMap(PaymentRequest paymentRequest) {
        Map<String,Object> jsonBody = new HashMap<>();
        jsonBody.put("email", paymentRequest.getEmail());
        jsonBody.put("amount", paymentRequest.getPrice());
        jsonBody.put("reference", paymentRequest.getReference());
        return jsonBody;
    }

    private Booking loadUsername(String bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new BookingNotFound("booking not made!"));
    }
//
//    @Scheduled(fixedRate = 180000)
//    private void pollingPayStack() throws URISyntaxException, IOException, InterruptedException {
//        List<Booking> bookingList =bookingRepository.findByPaymentStatus(PaymentStatus.PAYMENT_PENDING);
//        if(bookingList.isEmpty()) return;
//        for(Booking booking : bookingList){
//            String reference = booking.getPaymentId();
//            URI url = new URI("https://api.paystack.co/transaction/verify/"+ reference);
//
//            HttpClient client =  HttpClient.newBuilder().build();
//            HttpRequest request = HttpRequest.newBuilder()
//                    .GET()
//                    .uri(url)
//                    .header("authorization","Bearer"+payStackSecretKey)
//                    .build();
//            HttpResponse<String> response =client.send(request, HttpResponse.BodyHandlers.ofString());
//
//        }
//    }
//
//    private


}
