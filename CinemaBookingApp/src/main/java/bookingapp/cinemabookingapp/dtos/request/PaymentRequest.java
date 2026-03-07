package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
@Data
public class PaymentRequest{
    @NotBlank(message = "can not be blank")
    private String bookingId;
    @Min(value = 50)
    private BigDecimal price;
    @NotBlank(message = "can not be blank")
    private String reference;

}
