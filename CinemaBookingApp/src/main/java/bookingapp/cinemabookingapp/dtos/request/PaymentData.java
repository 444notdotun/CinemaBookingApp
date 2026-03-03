package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class PaymentData {
    private String reference;
    private int amount;
    private String status;
}
