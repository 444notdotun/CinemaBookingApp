package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.dtos.response.CreateAdminResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SuperAdminService extends UserDetailsService {
CreateAdminResponse createTheaterAdmin(CreateAdminRequest createAdminRequest);
}
