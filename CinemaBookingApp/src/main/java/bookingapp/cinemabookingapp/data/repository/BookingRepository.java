package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.PaymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository("Booking")
public interface BookingRepository extends MongoRepository<Booking,String> {
    List<Booking> findByPaymentStatusInAndExpirationTimeBefore(PaymentStatus paymentStatus, LocalDateTime expirationTime);
    Optional<Booking> findByPaymentId(String paymentId);
    List<Booking> findByPaymentStatus(PaymentStatus paymentStatus);
}
