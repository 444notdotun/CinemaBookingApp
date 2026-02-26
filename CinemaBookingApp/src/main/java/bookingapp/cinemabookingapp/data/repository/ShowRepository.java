package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Show;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShowRepository extends MongoRepository<Show,String> {
    boolean existsById(String id);
}
