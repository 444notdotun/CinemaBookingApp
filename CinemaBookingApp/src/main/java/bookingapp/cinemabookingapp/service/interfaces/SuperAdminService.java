package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.CreateAdminRequest;
import bookingapp.cinemabookingapp.dtos.response.CreateAdminResponse;

public interface SuperAdminService {
CreateAdminResponse createTheaterAdmin(CreateAdminRequest createAdminRequest);
}
