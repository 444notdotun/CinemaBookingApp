package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Booking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends MongoRepository<Booking,String> {

}
