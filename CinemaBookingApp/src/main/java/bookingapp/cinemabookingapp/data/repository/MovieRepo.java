package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Movies;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepo extends MongoRepository<Movies,String> {
    Optional<Movies> findMovieByMovieId(String id);
    boolean existsMovieByMovieId(String id);

}
