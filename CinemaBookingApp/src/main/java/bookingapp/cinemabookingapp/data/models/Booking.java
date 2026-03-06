package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Document
@Data
public class Booking {
    private String id;
    private String showId;
    private String seatNumber;
    private PaymentStatus paymentStatus;
    private String paymentId;
    private LocalTime expirationTime;
    private LocalTime paymentTime;
    private String userName;

    private LocalDateTime bookedAt;
    private BigDecimal bookingPrice;

    public Booking(){
        this.expirationTime = LocalTime.now().plusMinutes(10);
        this.bookedAt = LocalDateTime.now();
        this.paymentStatus = PaymentStatus.PAYMENT_PENDING;
        this.paymentId=UUID.randomUUID().toString();
    }

}
