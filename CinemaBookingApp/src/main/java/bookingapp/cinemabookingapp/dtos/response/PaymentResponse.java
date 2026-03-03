package bookingapp.cinemabookingapp.dtos.response;
import lombok.Data;

@Data
public class PaymentResponse {
    private String message;
    private boolean status;
    private PayStackResponse data;

}
