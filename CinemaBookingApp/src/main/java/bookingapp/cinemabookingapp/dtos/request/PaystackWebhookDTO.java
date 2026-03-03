package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class PaystackWebhookDTO {
    private String event;
    private PaymentData data;
}