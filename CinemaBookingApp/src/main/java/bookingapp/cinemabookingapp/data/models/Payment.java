package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;


@AllArgsConstructor
@Data
public class Payment {
private  int id;
private PaymentStatus paymentStatus;
}
