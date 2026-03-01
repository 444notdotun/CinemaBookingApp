package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalTime;


@AllArgsConstructor
@Data
public class Payment {
private  int id;
private PaymentStatus paymentStatus;
private LocalTime time;

public Payment(){
    this.paymentStatus = PaymentStatus.PAYMENT_PENDING;
}
}
