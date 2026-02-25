package bookingapp.cinemabookingapp.data.repository;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends MongoRepository<Admin,String> {
    Optional<Admin> findAdminById(String id);
    boolean existsAdminById(String id);
    boolean existsAdminByRole(Role role);
}
