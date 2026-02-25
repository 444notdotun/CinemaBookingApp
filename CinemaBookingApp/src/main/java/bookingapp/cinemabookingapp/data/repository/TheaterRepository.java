package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Theater;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TheaterRepository extends MongoRepository<Theater,String> {
      Theater findByName(String name);
      boolean existsByName(String name);
}
