package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Document
@Data
public class Booking {
    private String id;
    private String showId;
    private String seatNumber;
    private PaymentStatus paymentStatus;
    private LocalTime expirationTime;
    private String userName;
    private LocalDateTime createdAt;
    private LocalDateTime BookedAt;

    public Booking(){
        this.expirationTime = LocalTime.now().plusMinutes(1);
        this.createdAt = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PAYMENT_PENDING;
    }

}
