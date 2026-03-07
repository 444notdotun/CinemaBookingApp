package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PaymentData {

    private String reference;
    private int amount;
    private String status;
}
