package bookingapp.cinemabookingapp.dtos.response;

import bookingapp.cinemabookingapp.data.models.PaymentStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class Receipt {
    private String showId;
    private String bookingPrice;
    private String movieName;
    private String email;
    private PaymentStatus paymentStatus;
    private String paymentId;
    private LocalTime paymentTime;
    private LocalDateTime bookedAt;
    private LocalDateTime createdAt;
}
