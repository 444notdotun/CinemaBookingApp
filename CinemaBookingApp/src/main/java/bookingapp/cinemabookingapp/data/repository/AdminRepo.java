package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Admin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface AdminRepo extends MongoRepository<Admin,String> {
    Optional<Admin> findAdminById(String id);
    boolean existsAdminById(String id);
}
