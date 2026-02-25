package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Booking {
    private String id;
    private Show show;
    private String seatNumber;
    private PaymentStatus bookingStatus;
    private Payment payment;

}
