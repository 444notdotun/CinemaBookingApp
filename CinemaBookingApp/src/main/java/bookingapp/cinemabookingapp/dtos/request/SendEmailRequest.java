package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class SendEmailRequest {
    @Email(message = "should be an email")
    private String email;
    private  String bookingId;
}
