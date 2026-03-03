package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class PaymentRequest {
    private String bookingId;
    private BigDecimal price;
    private String reference;

}
